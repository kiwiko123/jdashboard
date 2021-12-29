package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters;

import com.kiwiko.library.persistence.data.api.interfaces.DataEntity;

@FunctionalInterface
public interface SaveEntity<T extends DataEntity> {

    T apply(T entity);
}
