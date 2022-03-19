package com.kiwiko.jdashboard.webapp.framework.application.properties.internal;

import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Properties;
import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Property;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.console.ConsoleLogger;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.exceptions.PropertyNotFoundException;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.GetPropertyInput;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.function.Consumer;

public class InMemoryJdashboardPropertyFileReader implements JdashboardPropertyReader {

    @Inject private JdashboardPropertyLoader propertyLoader;

    private Properties<String> properties;

    /**
     * Don't create a logger through dependency injection because the default logger implementation reads the properties file.
     * Otherwise, it will be a circular dependency.
     *
     * @see com.kiwiko.jdashboard.framework.monitoring.logging.impl.ConfigurationLogger
     */
    private Logger logger;

    public InMemoryJdashboardPropertyFileReader() {
        properties = new Properties<>();
        logger = new ConsoleLogger();
    }

    @Nullable
    @Override
    public Property<String> store(String propertyName) {
        return getProperty(propertyName, properties::addProperty);
    }

    @Nullable
    @Override
    public Property<String> get(String propertyName) {
        return getProperty(propertyName, null);
    }

    @Override
    public Property<String> get(GetPropertyInput input) throws PropertyNotFoundException {
        return input.getCacheControls().getShouldCache()
                ? store(input.getPropertyName())
                : get(input.getPropertyName());
    }

    private Properties<String> loadProperties() {
        return propertyLoader.loadProperties();
    }

    private Property<String> getProperty(String propertyName, @Nullable Consumer<Property<String>> actOnProperty) {
        Property<String> existingProperty = properties.getProperty(propertyName);
        if (existingProperty != null) {
            return existingProperty;
        }

        Properties<String> properties = loadProperties();
        Property<String> property = properties.getProperty(propertyName);
        if (actOnProperty != null && property != null) {
            actOnProperty.accept(property);
        }

        return property;
    }
}
