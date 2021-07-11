package com.kiwiko.webapp.persistence.data.api.interfaces;

import com.kiwiko.library.persistence.dataAccess.data.DataEntityDTO;

public class SoftDeletableDataEntityDTO extends DataEntityDTO {

    private boolean isRemoved;

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
