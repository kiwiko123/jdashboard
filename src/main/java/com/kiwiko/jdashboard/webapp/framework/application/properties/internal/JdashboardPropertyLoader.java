package com.kiwiko.jdashboard.webapp.framework.application.properties.internal;

import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Properties;
import com.kiwiko.jdashboard.library.files.properties.readers.api.dto.Property;
import com.kiwiko.jdashboard.library.files.properties.readers.api.interfaces.exceptions.PropertyFileException;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.console.ConsoleLogger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JdashboardPropertyLoader {

    @Inject private JdashboardPropertyFileNormalizer propertyFileParser;
    @Inject private JdashboardPropertyFileIdentifier propertyFileIdentifier;

    private final ConsoleLogger logger = new ConsoleLogger();

    public Properties<String> loadProperties() {
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
}
