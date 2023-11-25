package com.kiwiko.jdashboard.framework.data;

import javax.annotation.Nonnull;

public interface DataEntity<T> {

    @Nonnull
    T getId();
}
