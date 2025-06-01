package com.kiwiko.jdashboard.library.files.properties.readers.api.interfaces;

import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Property;

import javax.annotation.Nullable;

public interface PropertyReader<T> {

    void load();

    @Nullable
    Property<T> get(String propertyName);
}
