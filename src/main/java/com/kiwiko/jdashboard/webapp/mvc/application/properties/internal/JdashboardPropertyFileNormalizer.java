package com.kiwiko.jdashboard.webapp.mvc.application.properties.internal;

import com.kiwiko.library.files.properties.readers.api.interfaces.parsers.PropertyFileParser;

import java.util.Optional;

public class JdashboardPropertyFileNormalizer extends PropertyFileParser {

    public Optional<String> processLine(String line) {
        return Optional.of(line)
                .map(this::normalizeLine)
                .filter(this::shouldProcessLine);
    }

    private String normalizeLine(String line) {
        return line.strip();
    }

    private boolean shouldProcessLine(String line) {
        if (line.isEmpty()) {
            return false;
        }

        // Ignore comments.
        if (line.startsWith("#")) {
            return false;
        }

        return true;
    }
}
