package com.kiwiko.jdashboard.webapp.mvc.application.properties.api.interfaces;

import com.kiwiko.library.files.properties.readers.api.dto.Property;

import javax.annotation.Nullable;

/**
 * Retrieve a property from a Jdashboard properties file.
 */
public interface JdashboardPropertyReader {

    /**
     * Load and return the given property.
     * If the property is already stored in-memory (e.g., from a prior call to {@link #store(String)}),
     * it is immediately returned and the properties file is not re-read.
     *
     * @param propertyName the name of the property to retrieve
     * @return the matching property, or null if it isn't defined in the properties file
     */
    @Nullable
    Property<String> get(String propertyName);

    /**
     * Load and return the given property. The property is stored in-memory for future accesses.
     * If the property is already stored in-memory, it is immediately returned and the properties file is not re-read.
     *
     * Jdashboard may clear stored properties from memory at-will;
     * do not rely on this method to guarantee a property is always cached.
     *
     * @param propertyName the name of the property to retrieve and store
     * @return the matching property, or null if it isn't defined in the properties file
     */
    @Nullable
    Property<String> store(String propertyName);
}
