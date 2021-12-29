package com.kiwiko.jdashboard.webapp.monitoring.logging.impl;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.metrics.data.LogLevel;
import com.kiwiko.library.metrics.impl.ConsoleLogger;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyConstants;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyMapper;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ConfigurationLogger extends ConsoleLogger {

    @Inject private JdashboardPropertyReader jdashboardPropertyReader;
    @Inject private JdashboardPropertyMapper jdashboardPropertyMapper;

    @Override
    protected boolean shouldLog(LogLevel logLevel) {
        Property<Set<String>> allowedLevels = jdashboardPropertyMapper.mapToCollection(
                jdashboardPropertyReader.store(JdashboardPropertyConstants.APPLICATION_LOGGING_ALLOWED_LEVELS),
                HashSet::new);

        return Optional.ofNullable(allowedLevels.getValue())
                .map(levels -> levels.contains(logLevel.getName()))
                .orElse(false);
    }
}
