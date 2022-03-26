package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal;

import com.google.gson.Gson;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.SuccessConfidenceLevel;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters.SaveDataChangeCaptureParameters;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.framework.requests.api.CurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.exceptions.EntityChangeDataCaptureException;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class EntityChangeDataCapturer {
    @Inject private CurrentRequestService currentRequestService;
    @Inject private GsonProvider gsonProvider;
    @Inject private Logger logger;
    @Inject private TableRecordVersionClient tableRecordVersionClient;

    public <T extends DataEntity> T save(SaveDataChangeCaptureParameters<T> parameters) throws EntityChangeDataCaptureException {
        Objects.requireNonNull(parameters, "Input parameters required");
        Objects.requireNonNull(parameters.getCaptureDataChanges(), "@CaptureDataChange annotation object required");
        Objects.requireNonNull(parameters.getEntity(), "Entity subject required");
        Objects.requireNonNull(parameters.getGetEntityById(), "getById method required");
        Objects.requireNonNull(parameters.getSaveEntity(), "save method required");

        Long id = parameters.getEntity().getId();
        boolean isNewEntity = id == null;
        if (isNewEntity) {
            return saveNewEntity(parameters);
        }

        return saveExistingEntity(parameters);
    }

    private <T extends DataEntity> T saveNewEntity(SaveDataChangeCaptureParameters<T> parameters) {
        T entity = parameters.getEntity();
        T savedEntity = parameters.getSaveEntity().apply(entity);
        Map<String, Object> fieldValuesByName = getAllFields(entity);
        recordChanges(parameters.getCaptureDataChanges(), savedEntity, fieldValuesByName);

        return savedEntity;
    }

    private <T extends DataEntity> T saveExistingEntity(SaveDataChangeCaptureParameters<T> parameters) {
        T newEntity = parameters.getEntity();
        T existingEntity = parameters.getGetEntityById().apply(newEntity.getId())
                .orElseThrow(() -> new EntityChangeDataCaptureException(String.format("No existing entity found: %s", parameters.getEntity().toString())));

        Map<String, Object> updatedValuesByName = getUpdatedFields(existingEntity, newEntity);
        recordChanges(parameters.getCaptureDataChanges(), newEntity, updatedValuesByName);

        return parameters.getSaveEntity().apply(newEntity);
    }

    private <T extends DataEntity> Map<String, Method> getEligibleFields(T entity) {
        Map<String, Method> gettersByFieldName = new HashMap<>();
        Class<?> entityType = entity.getClass();

        for (Method method : entityType.getDeclaredMethods()) {
            if (!method.getName().startsWith("get")) {
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

    private <T extends DataEntity> void recordChanges(
            CaptureDataChanges captureDataChanges,
            T entity,
            Map<String, Object> diff) {
        validateEntity(entity);
        String tableName = Optional.ofNullable(entity.getClass().getDeclaredAnnotation(Table.class))
                .map(Table::name)
                .orElseThrow(() -> new EntityChangeDataCaptureException(String.format("No @Table name annotation found: %s", entity.toString())));

        Gson gson = gsonProvider.getDefault();
        String jsonChanges = gson.toJson(diff);

        TableRecordVersion version = new TableRecordVersion();
        version.setTableName(tableName);
        version.setRecordId(entity.getId());
        version.setChanges(jsonChanges);
        currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .ifPresent(version::setCreatedByUserId);

        createTableRecordVersion(captureDataChanges, version, entity);
    }

    private <T extends DataEntity> void validateEntity(T entity) throws EntityChangeDataCaptureException {
        if (entity.getId() == null) {
            throw new EntityChangeDataCaptureException(String.format("ID is required to capture entity data changes: %s", entity.toString()));
        }
    }

    private void createTableRecordVersion(
            CaptureDataChanges captureDataChanges,
            TableRecordVersion tableRecordVersion,
            DataEntity entity) {
        CreateTableRecordVersionInput createTableRecordVersionInput = new CreateTableRecordVersionInput();
        createTableRecordVersionInput.setTableRecordVersion(tableRecordVersion);

        logger.debug("Capturing entity data changes for {} at confidence level {}", entity, captureDataChanges.successConfidence());

        switch (captureDataChanges.successConfidence()) {
            case OPTIMISTIC:
                recordChangesOptimistically(createTableRecordVersionInput, captureDataChanges, entity);
                break;
            case CONFIDENT:
            default:
                recordChangesConfidently(createTableRecordVersionInput, captureDataChanges, entity);
        }
    }

    private void recordChangesOptimistically(CreateTableRecordVersionInput input, CaptureDataChanges captureDataChanges, DataEntity entity) {
        tableRecordVersionClient.createAsync(input)
                .thenAccept(response -> validateCreateTableRecordVersionResponse(response, captureDataChanges, entity));
    }

    private void recordChangesConfidently(CreateTableRecordVersionInput input, CaptureDataChanges captureDataChanges, DataEntity entity) {
        ClientResponse<CreateTableRecordVersionOutput> response = tableRecordVersionClient.create(input);
        validateCreateTableRecordVersionResponse(response, captureDataChanges, entity);
    }

    private void validateCreateTableRecordVersionResponse(
            ClientResponse<CreateTableRecordVersionOutput> response,
            CaptureDataChanges captureDataChanges,
            DataEntity entity) {
        ResponseStatus status = response.getStatus();
        if (status.isSuccessful()) {
            return;
        }

        logger.error(
                "Error capturing data changes for entity {} with status {} and message {}",
                entity,
                status.getStatusCode(),
                status.getErrorMessage());

        if (captureDataChanges.successConfidence() == SuccessConfidenceLevel.CONFIDENT) {
            throw new EntityChangeDataCaptureException(String.format("Error capturing data changes for entity %s", entity));
        }
    }
}
