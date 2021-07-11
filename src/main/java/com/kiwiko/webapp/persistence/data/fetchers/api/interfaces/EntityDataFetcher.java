package com.kiwiko.webapp.persistence.data.fetchers.api.interfaces;

import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;
import com.kiwiko.webapp.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.webapp.persistence.data.api.interfaces.SoftDeletableDataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.CaptureEntityDataChanges;
import com.kiwiko.webapp.persistence.data.cdc.internal.DataChangeCapturer;

import javax.inject.Inject;

public abstract class EntityDataFetcher<T extends DataEntity> extends EntityManagerDAO<T> {

    @Inject private DataChangeCapturer dataChangeCapturer;

    private final boolean captureDataChanges;

    public EntityDataFetcher() {
        super();
        captureDataChanges = entityType.getAnnotation(CaptureEntityDataChanges.class) != null;
    }

    @Override
    public T save(T entity) {
        if (captureDataChanges) {
            return dataChangeCapturer.save(entity, this::getById, super::save);
        }

        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof SoftDeletableDataEntity) {
            SoftDeletableDataEntity softDeletableDataEntity = (SoftDeletableDataEntity) entity;
            softDeletableDataEntity.setIsRemoved(true);
            save(entity);
            return;
        }

        super.delete(entity);
    }
}
