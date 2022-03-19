package com.kiwiko.jdashboard.framework.monitoring.logging.impl;

import com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.dto.LogLevel;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.console.ConsoleLogger;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyConstants;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.GetPropertyInput;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.PropertyCacheControls;

import javax.inject.Inject;
import java.util.Set;

public class ConfigurationLogger extends ConsoleLogger {

    @Inject private JdashboardPropertyReader jdashboardPropertyReader;

    @Override
    protected boolean shouldLog(LogLevel logLevel) {
        PropertyCacheControls propertyCacheControls = PropertyCacheControls.indefinitely();
        GetPropertyInput getPropertyInput = new GetPropertyInput(JdashboardPropertyConstants.APPLICATION_LOGGING_ALLOWED_LEVELS, propertyCacheControls);
        Set<String> allowedLevels = jdashboardPropertyReader.get(getPropertyInput).mappable().mapToSet();

        return allowedLevels.contains(logLevel.getName());
    }
}
