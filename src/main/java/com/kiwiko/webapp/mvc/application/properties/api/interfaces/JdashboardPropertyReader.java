package com.kiwiko.webapp.mvc.application.properties.api.interfaces;

import com.kiwiko.library.files.properties.readers.api.dto.Property;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface JdashboardPropertyReader {

    @Nullable
    Property<String> get(String propertyName);

    @Nullable
    Property<List<String>> getList(String propertyName);

    @Nullable
    <T extends Collection<String>> Property<T> getCollection(String propertyName, Function<List<String>, T> mapToCollection);
}
