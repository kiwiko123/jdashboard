package com.kiwiko.webapp.mvc.application.properties.internal;

import com.kiwiko.library.files.properties.readers.api.dto.Properties;
import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JdashboardPropertyFileReader implements JdashboardPropertyReader {

    @Inject private JdashboardPropertyFileParser propertyFileParser;
    @Inject private JdashboardPropertyFileIdentifier propertyFileIdentifier;
    @Inject private Logger logger;

    private Properties<String> properties;

    private void load() {
        String propertyFilePath = String.format("properties/%s", propertyFileIdentifier.getPropertiesFile());
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyFilePath)) {
            properties = makeProperties(stream);
        } catch (IOException e) {
            logger.error("Error parsing Jdashboard properties file", e);
        }
    }

    @Nullable
    @Override
    public Property<String> get(String propertyName) {
        if (properties == null) {
            logger.info("No Jdashboard properties file found; loading now");
            load();
        }
        return properties.getProperty(propertyName);
    }

    @Override
    public Property<List<String>> getList(String propertyName) {
        return getCollection(propertyName, LinkedList::new);
    }

    @Override
    public <T extends Collection<String>> Property<T> getCollection(
            String propertyName,
            Function<List<String>, T> mapToCollection) {
        T values = Optional.ofNullable(get(propertyName))
                .map(Property::getValue)
                .map(value -> value.split(","))
                .map(Arrays::asList)
                .map(mapToCollection)
                .orElse(null);

        return new Property<>(propertyName, values);
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
}
