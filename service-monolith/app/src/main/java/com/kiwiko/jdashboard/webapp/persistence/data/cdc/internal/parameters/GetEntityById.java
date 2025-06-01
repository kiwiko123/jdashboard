package com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

import java.util.Optional;

@FunctionalInterface
public interface GetEntityById<T extends LongDataEntity> {

    Optional<T> apply(long id);
}
