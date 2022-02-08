package com.kiwiko.jdashboard.webapp.framework.application.properties.internal;

import com.kiwiko.jdashboard.library.files.properties.readers.api.interfaces.exceptions.PropertyFileException;
import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;
import com.kiwiko.jdashboard.webapp.framework.security.environments.data.EnvironmentType;

import javax.inject.Inject;

public class JdashboardPropertyFileIdentifier {

    @Inject private EnvironmentService environmentService;

    public String getPropertiesFile() {
        EnvironmentType environmentType = environmentService.getEnvironmentType();
        switch (environmentType) {
            case TEST:
            case LOCAL:
                return JdashboardPropertyFileConstants.DEV_PROPERTIES_FILE_NAME;
            // TODO support other environments
        }

        throw new PropertyFileException(String.format("No properties file found for environment %s", environmentType.name()));
    }
}
