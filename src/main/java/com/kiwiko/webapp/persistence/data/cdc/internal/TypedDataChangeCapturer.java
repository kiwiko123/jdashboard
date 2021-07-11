package com.kiwiko.webapp.persistence.data.cdc.internal;

import com.google.gson.Gson;
import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.json.gson.GsonProvider;
import com.kiwiko.webapp.mvc.requests.api.CurrentRequestService;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.exceptions.CaptureEntityDataChangeException;
import com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import com.kiwiko.webapp.users.data.User;

import javax.inject.Inject;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class TypedDataChangeCapturer<T extends DataEntity> {
    private static final Set<String> IGNORED_VERSION_FIELD_NAMES = Set.of("versions");

    @Inject private CurrentRequestService currentRequestService;
    @Inject private GsonProvider gsonProvider;
    @Inject private Logger logger;
    @Inject private ReflectionHelper reflectionHelper;
    @Inject private TableRecordVersionService tableRecordVersionService;

    public T save(T entity, Function<Long, Optional<T>> getById, Function<T, T> saveEntity) {
        Long id = entity.getId();
        boolean isNewEntity = id == null;
        if (isNewEntity) {
            return saveNewEntity(entity, saveEntity);
        }

        T existingEntity = getById.apply(id)
                .orElseThrow(() -> new CaptureEntityDataChangeException(String.format("No existing entity found: %s", entity.toString())));
        return saveExistingEntity(existingEntity, entity, saveEntity);
    }

    private T saveNewEntity(T entity, Function<T, T> saveEntity) {
        T savedEntity = saveEntity.apply(entity);
        Map<String, Object> fieldValuesByName = getAllFields(entity);
        recordChanges(savedEntity, fieldValuesByName);

        return savedEntity;
    }

    private T saveExistingEntity(T existingEntity, T newEntity, Function<T, T> saveEntity) {
        Map<String, Object> updatedValuesByName = getUpdatedFields(existingEntity, newEntity);
        recordChanges(newEntity, updatedValuesByName);

        return saveEntity.apply(newEntity);
    }

    private Set<Field> getEligibleFields(T entity) {
        return reflectionHelper.getFields(entity.getClass()).stream()
                .filter(field -> !IGNORED_VERSION_FIELD_NAMES.contains(field.getName()))
                .collect(Collectors.toUnmodifiableSet());
    }

    private Map<String, Object> getAllFields(T entity) {
        Map<String, Object> valuesByName = new HashMap<>();

        for (Field field : getEligibleFields(entity)) {
            Object value;
            field.trySetAccessible();

            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                logger.error(String.format("Error accessing %s field %s for version recording", entity.getClass().getName(), field.getName()), e);
                return new HashMap<>();
            }

            valuesByName.put(field.getName(), value);
        }

        return valuesByName;
    }

    private Map<String, Object> getUpdatedFields(T existingEntity, T newEntity) {
        Map<String, Object> valuesByName = new HashMap<>();

        for (Field field : getEligibleFields(newEntity)) {
            Object currentValue;
            Object updatedValue;

            field.trySetAccessible();
            try {
                currentValue = field.get(existingEntity);
                updatedValue = field.get(newEntity);
            } catch (IllegalAccessException e) {
                logger.error(String.format("Error accessing %s field %s for version recording", newEntity.getClass().getName(), field.getName()), e);
                return new HashMap<>();
            }

            if (!Objects.equals(currentValue, updatedValue)) {
                valuesByName.put(field.getName(), updatedValue);
            }
        }

        return valuesByName;
    }

    private void recordChanges(T entity, Map<String, Object> diff) {
        validateEntity(entity);
        String tableName = Optional.ofNullable(entity.getClass().getDeclaredAnnotation(Table.class))
                .map(Table::name)
                .orElseThrow(() -> new CaptureEntityDataChangeException(String.format("No @Table name annotation found: %s", entity.toString())));

        Gson gson = gsonProvider.getDefault();
        String jsonChanges = gson.toJson(diff);

        TableRecordVersion version = new TableRecordVersion();
        version.setTableName(tableName);
        version.setRecordId(entity.getId());
        version.setChanges(jsonChanges);
        currentRequestService.getCurrentRequestContext()
                .flatMap(RequestContext::getUser)
                .map(User::getId)
                .ifPresent(version::setCreatedByUserId);

        tableRecordVersionService.create(version);
    }

    private void validateEntity(T entity) throws CaptureEntityDataChangeException {
        if (entity.getId() == null) {
            throw new CaptureEntityDataChangeException(String.format("ID is required to capture entity data changes: %s", entity.toString()));
        }
    }
}
