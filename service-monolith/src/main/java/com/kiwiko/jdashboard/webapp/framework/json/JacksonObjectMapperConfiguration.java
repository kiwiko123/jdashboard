package com.kiwiko.jdashboard.webapp.framework.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonObjectMapperConfiguration implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder
                // Don't serialize extraneous properties like getters or setters.
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)

                // Allow detection of private default constructors.
                .visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY)

                // Serialize all fields.
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
