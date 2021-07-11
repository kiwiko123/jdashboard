package com.kiwiko.webapp.persistence.data.api.interfaces;

public interface SoftDeletableDataEntity extends DataEntity {

    boolean getIsRemoved();
    void setIsRemoved(boolean isRemoved);
}
