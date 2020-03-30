package com.kiwiko.persistence.dataAccess.api;

import com.kiwiko.persistence.identification.Identifiable;

import java.util.Objects;

public abstract class DataEntity implements Identifiable<Long> {

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        DataEntity otherEntity = (DataEntity) other;
        return Objects.equals(getId(), otherEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
