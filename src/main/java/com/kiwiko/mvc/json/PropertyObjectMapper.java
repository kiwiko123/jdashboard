package com.kiwiko.mvc.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.util.Collection;

/**
 * Wrapper class around {@link ObjectMapper} to allow convenient {@link javax.inject.Inject}ion into classes.
 */
public class PropertyObjectMapper {

    private final ObjectMapper objectMapper;

    public PropertyObjectMapper() {
        objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    }

    public <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(content, valueType);
    }

    public <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    /**
     * @param fromValue the value to be converted.
     * @param toCollectionType the derivative of {@link Collection} to which the value will be converted.
     * @param collectionOfValueType the type of object that the collection holds.
     * @param <T> the collection type.
     * @return
     * @throws IllegalArgumentException if the conversion fails.
     */
    public <T extends Collection> T convertCollectionValue(Object fromValue, Class<T> toCollectionType, Class<?> collectionOfValueType) throws IllegalArgumentException {
        JavaType collectionType = objectMapper.getTypeFactory()
                .constructCollectionType(toCollectionType, collectionOfValueType);
        return objectMapper.convertValue(fromValue, collectionType);
    }
}
