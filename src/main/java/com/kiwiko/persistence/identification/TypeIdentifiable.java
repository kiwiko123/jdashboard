package com.kiwiko.persistence.identification;

import java.util.Objects;

public abstract class TypeIdentifiable<T> implements Identifiable<T> {

    @Override
    public String toString() {
        if (getId() == null) {
            return super.toString();
        }
        return String.format("%s(id=%s)", getClass().getSimpleName(), getId().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().isAssignableFrom(other.getClass())) {
            return false;
        }

        Identifiable<T> otherIdentifiable = (Identifiable<T>) other;
        return Objects.equals(getId(), otherIdentifiable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
