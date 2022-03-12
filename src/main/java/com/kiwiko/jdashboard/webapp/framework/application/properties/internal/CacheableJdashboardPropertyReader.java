package com.kiwiko.jdashboard.webapp.framework.application.properties.internal;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Properties;
import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Property;
import com.kiwiko.jdashboard.library.metrics.impl.ConsoleLogger;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.exceptions.PropertyNotFoundException;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.GetPropertyInput;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.PropertyCacheControls;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;

public class CacheableJdashboardPropertyReader implements JdashboardPropertyReader {

    private static final String CACHE_KEY_PREFIX = "__JDASHBOARD_CacheableJdashboardPropertyReader_PROPERTY";

    @Inject private JdashboardPropertyLoader propertyLoader;
    @Inject private ObjectCache objectCache;

    /**
     * Don't create a logger through dependency injection because the default logger implementation reads the properties file.
     * Otherwise, it will be a circular dependency.
     *
     * @see com.kiwiko.jdashboard.framework.monitoring.logging.impl.ConfigurationLogger
     */
    private final ConsoleLogger logger = new ConsoleLogger();

    @Override
    public Property<String> get(GetPropertyInput input) throws PropertyNotFoundException {
        String propertyName = input.getPropertyName();
        PropertyCacheControls cacheControls = input.getCacheControls();

        if (!cacheControls.getShouldCache()) {
            return loadProperty(propertyName);
        }

        Optional<Property<String>> cachedProperty = loadPropertyFromCache(propertyName);
        if (cachedProperty.isPresent()) {
            return cachedProperty.get();
        }

        Property<String> property = loadProperty(propertyName);
        cacheProperty(property, cacheControls);
        return property;
    }

    @Nullable
    @Override
    public Property<String> get(String propertyName) {
        GetPropertyInput input = new GetPropertyInput(propertyName, PropertyCacheControls.disabled());
        try {
            return get(input);
        } catch (PropertyNotFoundException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public Property<String> store(String propertyName) {
        GetPropertyInput input = new GetPropertyInput(propertyName, PropertyCacheControls.indefinitely());
        try {
            return get(input);
        } catch (PropertyNotFoundException e) {
            return null;
        }
    }

    private Property<String> loadProperty(String propertyName) throws PropertyNotFoundException {
        Properties<String> properties = propertyLoader.loadProperties();
        return Optional.ofNullable(properties.getProperty(propertyName))
                .orElseThrow(() -> new PropertyNotFoundException(String.format("No property found with name \"%s\"", propertyName)));
    }

    private String makeCacheKey(String propertyName) {
        return String.format("%s:%s", CACHE_KEY_PREFIX, propertyName);
    }

    private Optional<Property<String>> loadPropertyFromCache(String propertyName) {
        String cacheKey = makeCacheKey(propertyName);
        return objectCache.get(cacheKey);
    }

    private void cacheProperty(Property<String> property, PropertyCacheControls cacheControls) {
        String cacheKey = makeCacheKey(property.getName());
        if (cacheControls.getCacheDuration() == null) {
            objectCache.cache(cacheKey, property);
        } else {
            objectCache.cache(cacheKey, property, cacheControls.getCacheDuration());
        }
    }
}
