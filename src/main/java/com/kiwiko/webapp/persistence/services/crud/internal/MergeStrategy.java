package com.kiwiko.webapp.persistence.services.crud.internal;

public enum MergeStrategy {
    DEFAULT,
    SET_NON_NULL;

    public MergeStrategy resolve() {
        if (this == DEFAULT) {
            return SET_NON_NULL;
        }
        return this;
    }
}
