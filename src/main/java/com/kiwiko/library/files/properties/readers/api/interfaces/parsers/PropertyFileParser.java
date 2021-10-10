package com.kiwiko.library.files.properties.readers.api.interfaces.parsers;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.files.properties.readers.api.interfaces.exceptions.PropertyParsingException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyFileParser {

    private static final Pattern EQUALS_PATTERN = Pattern.compile("^(?<name>[\\w.]+)=(?<value>.*)$");

    public Property<String> parseEquals(String line) {
        Matcher matcher = EQUALS_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new PropertyParsingException(String.format("Unable to parse property file line \"%s\"", line));
        }

        String name = matcher.group("name");
        String value = matcher.group("value");

        Objects.requireNonNull(name);
        Objects.requireNonNull(value);

        return new Property<>(name, value);
    }
}
