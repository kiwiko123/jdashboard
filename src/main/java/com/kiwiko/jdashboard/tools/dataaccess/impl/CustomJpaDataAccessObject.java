package com.kiwiko.jdashboard.tools.dataaccess.impl;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.EntityManagerProvider;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters.SaveDataChangeCaptureParameters;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * Data access object implementation with custom behavior. Derived classes should {@link javax.inject.Inject}
 * the required dependencies via constructor injection.
 *
 * Custom behavior provided includes:
 * <ul>
 *     <li>Custom {@link EntityManager} via an {@link EntityManagerProvider}.</li>
 *     <li>Change data capture as {@link com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion}s.</li>
 * </ul>
 *
 * @param <T>
 */
public abstract class CustomJpaDataAccessObject<T extends DataEntity> extends AbstractJpaDataAccessObject<T> {
    // Dependencies
    private final DataChangeCapturer dataChangeCapturer;
    private final Logger logger;

    // Stateful data.
    private final @Nullable CaptureDataChanges captureDataChanges;

    public CustomJpaDataAccessObject(
            EntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        super(entityManagerProvider);
        this.dataChangeCapturer = dataChangeCapturer;
        this.logger = logger;
        captureDataChanges = entityType.getAnnotation(CaptureDataChanges.class);
    }

    /**
     * Persists the given entity object into the database.
     * A read-write database transaction must be open (e.g., via {@link org.springframework.transaction.annotation.Transactional}).
     *
     * @param entity the entity to persist
     * @return the entity that was saved, which can possibly be managed (live database connection)
     */
    @Override
    public T save(T entity) {
        if (captureDataChanges != null) {
            return changeDataCaptureSave(entity);
        }

        return persistToDataStore(entity);
    }

    private T persistToDataStore(T entity) {
        return super.save(entity);
    }

    private T changeDataCaptureSave(T entity) {
        Objects.requireNonNull(dataChangeCapturer, "No data change capturer provided");
        Objects.requireNonNull(captureDataChanges, "@CaptureDataChanges is required");

        SaveDataChangeCaptureParameters<T> parameters = new SaveDataChangeCaptureParameters<>();
        parameters.setCaptureDataChanges(captureDataChanges);
        parameters.setEntity(entity);
        parameters.setGetEntityById(this::getById);
        parameters.setSaveEntity(this::persistToDataStore);

        try {
            return dataChangeCapturer.save(parameters);
        } catch (Exception e) {
            logger.error(String.format("Error capturing data change for entity %s", entity), e);
            if (captureDataChanges.exceptionOnFailure()) {
                throw e;
            }
        }

        return persistToDataStore(entity);
    }
}
