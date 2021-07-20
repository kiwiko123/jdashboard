package com.kiwiko.webapp.persistence.data.cdc.internal;

import com.google.gson.Gson;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.json.gson.GsonProvider;
import com.kiwiko.webapp.mvc.requests.api.CurrentRequestService;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.exceptions.CaptureEntityDataChangeException;
import com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import com.kiwiko.webapp.users.data.User;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DataChangeCapturer {
    private static final Set<String> IGNORED_VERSION_FIELD_NAMES = Set.of("versions");

    @Inject private CurrentRequestService currentRequestService;
    @Inject private GsonProvider gsonProvider;
    @Inject private Logger logger;
    @Inject private TableRecordVersionService tableRecordVersionService;

    public <T extends DataEntity> T save(T entity, Function<Long, Optional<T>> getById, Function<T, T> saveEntity) {
        Long id = entity.getId();
        boolean isNewEntity = id == null;
        if (isNewEntity) {
            return saveNewEntity(entity, saveEntity);
        }

        T existingEntity = getById.apply(id)
                .orElseThrow(() -> new CaptureEntityDataChangeException(String.format("No existing entity found: %s", entity.toString())));
        return saveExistingEntity(existingEntity, entity, saveEntity);
    }

    private <T extends DataEntity> T saveNewEntity(T entity, Function<T, T> saveEntity) {
        T savedEntity = saveEntity.apply(entity);
        Map<String, Object> fieldValuesByName = getAllFields(entity);
        recordChanges(savedEntity, fieldValuesByName);

        return savedEntity;
    }

    private <T extends DataEntity> T saveExistingEntity(T existingEntity, T newEntity, Function<T, T> saveEntity) {
        Map<String, Object> updatedValuesByName = getUpdatedFields(existingEntity, newEntity);
        recordChanges(newEntity, updatedValuesByName);

        return saveEntity.apply(newEntity);
    }

    private <T extends DataEntity> Map<String, Method> getEligibleFields(T entity) {
        Map<String, Method> gettersByFieldName = new HashMap<>();
        Class<?> entityType = entity.getClass();

        for (Method method : entityType.getDeclaredMethods()) {
            if (!method.getName().startsWith("get")) {
                logger.debug(String.format("Skipping column method %s; does not appear to be a getter", method.getName()));
                continue;
            }

            Column column = method.getDeclaredAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            Objects.requireNonNull(column.name(), "Column name is required");
            gettersByFieldName.put(column.name(), method);
        }

        return gettersByFieldName;
    }

    private <T extends DataEntity> Map<String, Object> getAllFields(T entity) {
        Map<String, Object> valuesByName = new HashMap<>();

        getEligibleFields(entity).forEach((columnName, getter) -> {
            Object value;

            try {
                value = getter.invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error(String.format("Error accessing %s field %s for version recording", entity.getClass().getName(), columnName), e);
                return;
            }

            valuesByName.put(columnName, value);
        });

        return valuesByName;
    }

    private <T extends DataEntity> Map<String, Object> getUpdatedFields(T existingEntity, T newEntity) {
        Map<String, Object> valuesByName = new HashMap<>();

        getEligibleFields(newEntity).forEach((columnName, getter) -> {
            Object currentValue;
            Object updatedValue;

            try {
                currentValue = getter.invoke(existingEntity);
                updatedValue = getter.invoke(newEntity);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error(String.format("Error accessing %s field %s for version recording", newEntity.getClass().getName(), columnName), e);
                return;
            }

            if (!Objects.equals(currentValue, updatedValue)) {
                valuesByName.put(columnName, updatedValue);
            }
        });

        return valuesByName;
    }

    private <T extends DataEntity> void recordChanges(T entity, Map<String, Object> diff) {
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

    private <T extends DataEntity> void validateEntity(T entity) throws CaptureEntityDataChangeException {
        if (entity.getId() == null) {
            throw new CaptureEntityDataChangeException(String.format("ID is required to capture entity data changes: %s", entity.toString()));
        }
    }
}
