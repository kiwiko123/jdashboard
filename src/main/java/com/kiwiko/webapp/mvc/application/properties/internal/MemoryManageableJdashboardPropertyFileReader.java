package com.kiwiko.webapp.mvc.application.properties.internal;

import com.kiwiko.library.files.properties.readers.api.dto.Properties;
import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.files.properties.readers.api.interfaces.exceptions.PropertyFileException;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class MemoryManageableJdashboardPropertyFileReader implements JdashboardPropertyReader {

    @Inject private JdashboardPropertyFileNormalizer propertyFileParser;
    @Inject private JdashboardPropertyFileIdentifier propertyFileIdentifier;
    @Inject private Logger logger;

    private Properties<String> properties;

    public MemoryManageableJdashboardPropertyFileReader() {
        properties = new Properties<>();
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

    private Properties<String> loadProperties() {
        String propertyFileName = propertyFileIdentifier.getPropertiesFile();
        logger.info(String.format("Loading Jdashboard properties file %s", propertyFileName));

        String propertyFilePath = String.format("properties/%s", propertyFileName);

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyFilePath)) {
            return makeProperties(stream);
        } catch (IOException e) {
            logger.error("Error parsing Jdashboard properties file", e);
            throw new PropertyFileException("Error parsing Jdashboard properties file", e);
        }
    }

    private Properties<String> makeProperties(InputStream stream) throws IOException {
        Properties<String> properties = new Properties<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = propertyFileParser.processLine(line).orElse(null);
                if (line == null) {
                    continue;
                }

                Property<String> property = propertyFileParser.parseEquals(line);
                properties.addProperty(property);
            }
        }

        return properties;
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
