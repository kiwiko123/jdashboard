package com.kiwiko.webapp.mvc.application.properties.api.interfaces;

import com.google.common.base.Splitter;
import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class JdashboardPropertyMapper {

    @Inject private Logger logger;

    public <T extends Collection<String>> Property<T> mapToCollection(
            Property<String> property,
            Function<List<String>, T> toCollection) {
        String rawValue = property.getValue();
        List<String> values;
        if (rawValue == null || rawValue.isBlank()) {
            logger.debug(String.format("Property %s is blank; cannot map to collection", property.getName()));
            values = new LinkedList<>();
        } else {
            values = Splitter.on(',').splitToList(rawValue);
        }

        return new Property<>(property.getName(), toCollection.apply(values));
    }

    public Property<List<String>> mapToList(Property<String> property) {
        return mapToCollection(property, LinkedList::new);
    }
}
