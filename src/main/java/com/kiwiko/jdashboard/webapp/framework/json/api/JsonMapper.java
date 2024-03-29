package com.kiwiko.jdashboard.webapp.framework.json.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwiko.jdashboard.webapp.framework.json.api.errors.JsonException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Wrapper class around {@link ObjectMapper} to allow convenient {@link javax.inject.Inject}ion into classes.
 *
 * @see com.kiwiko.jdashboard.webapp.framework.json.JacksonObjectMapperConfiguration#customize(Jackson2ObjectMapperBuilder)
 */
public class JsonMapper {

    @Inject
    private ObjectMapper objectMapper;

    public <T> T deserialize(String content, Class<T> valueType) throws JsonException {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public <T> T convertValue(Object fromValue, Class<T> toValueType) throws JsonException {
        try {
            return objectMapper.convertValue(fromValue, toValueType);
        } catch (IllegalArgumentException e) {
            throw new JsonException(e);
        }
    }

    public <T> String writeValueAsString(T value) throws JsonException {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            String message = String.format("Failed to serialize value of type %s into a string", value.getClass().getName());
            throw new JsonException(message, e);
        }
    }

    /**
     * @param fromValue the value to be converted.
     * @param toCollectionType the derivative of {@link Collection} to which the value will be converted.
     * @param collectionOfValueType the type of object that the collection holds.
     * @param <T> the collection type.
     * @return
     * @throws IllegalArgumentException if the conversion fails.
     */
    public <T extends Collection> T convertCollectionValue(Object fromValue, Class<T> toCollectionType, Class<?> collectionOfValueType) throws JsonException {
        JavaType collectionType = objectMapper.getTypeFactory()
                .constructCollectionType(toCollectionType, collectionOfValueType);
        try {
            return objectMapper.convertValue(fromValue, collectionType);
        } catch (IllegalArgumentException e) {
            throw new JsonException(e);
        }
    }
}
