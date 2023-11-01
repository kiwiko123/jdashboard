package com.kiwiko.jdashboard.library.persistence.data.api.interfaces;

public class SoftDeletableDataEntityDTO extends DataEntityDTO implements SoftDeletable {

    private boolean isRemoved;

    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
