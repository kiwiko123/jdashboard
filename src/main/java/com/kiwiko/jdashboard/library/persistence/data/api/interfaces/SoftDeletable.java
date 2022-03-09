package com.kiwiko.jdashboard.library.persistence.data.api.interfaces;

public interface SoftDeletable {

    boolean getIsRemoved();
    void setIsRemoved(boolean isRemoved);
}
