package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;

import java.util.Optional;

@FunctionalInterface
public interface GetEntityById<T extends DataEntity> {

    Optional<T> apply(long id);
}
