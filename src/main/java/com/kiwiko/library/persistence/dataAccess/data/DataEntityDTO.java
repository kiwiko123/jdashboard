package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Objects;

public class DataEntityDTO implements Identifiable<Long> {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d)", getClass().getSimpleName(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataEntityDTO)) {
            return false;
        }
        DataEntityDTO that = (DataEntityDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
