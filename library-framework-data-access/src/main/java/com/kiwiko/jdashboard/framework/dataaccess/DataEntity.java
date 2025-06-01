package com.kiwiko.jdashboard.framework.dataaccess;

import javax.annotation.Nonnull;

public interface DataEntity<T> {

    @Nonnull
    T getId();
}
