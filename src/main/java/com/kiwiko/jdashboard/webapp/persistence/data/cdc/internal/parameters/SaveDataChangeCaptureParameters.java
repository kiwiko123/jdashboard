package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters;

import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;

public class SaveDataChangeCaptureParameters<T extends DataEntity> {
    private T entity;
    private GetEntityById<T> getEntityById;
    private SaveEntity<T> saveEntity;
    private CaptureDataChanges captureDataChanges;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public GetEntityById<T> getGetEntityById() {
        return getEntityById;
    }

    public void setGetEntityById(GetEntityById<T> getEntityById) {
        this.getEntityById = getEntityById;
    }

    public SaveEntity<T> getSaveEntity() {
        return saveEntity;
    }

    public void setSaveEntity(SaveEntity<T> saveEntity) {
        this.saveEntity = saveEntity;
    }

    public CaptureDataChanges getCaptureDataChanges() {
        return captureDataChanges;
    }

    public void setCaptureDataChanges(CaptureDataChanges captureDataChanges) {
        this.captureDataChanges = captureDataChanges;
    }
}
