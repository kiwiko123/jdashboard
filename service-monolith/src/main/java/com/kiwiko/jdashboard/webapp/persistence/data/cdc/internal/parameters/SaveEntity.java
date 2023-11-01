package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

@FunctionalInterface
public interface SaveEntity<T extends LongDataEntity> {

    T apply(T entity);
}
