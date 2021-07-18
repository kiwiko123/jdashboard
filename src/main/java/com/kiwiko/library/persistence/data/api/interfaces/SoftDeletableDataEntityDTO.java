package com.kiwiko.library.persistence.data.api.interfaces;

public class SoftDeletableDataEntityDTO extends DataEntityDTO {

    private boolean isRemoved;

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
