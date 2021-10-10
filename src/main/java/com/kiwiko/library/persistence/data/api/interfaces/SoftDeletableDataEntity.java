package com.kiwiko.library.persistence.data.api.interfaces;

public interface SoftDeletableDataEntity extends DataEntity {

    boolean getIsRemoved();
    void setIsRemoved(boolean isRemoved);
}
