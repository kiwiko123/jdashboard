package com.kiwiko.webapp.mvc.json;

import com.kiwiko.library.lang.random.RandomUtil;
import com.kiwiko.library.lang.reflection.ReflectionHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfiguration {

    @Bean
    public ReflectionHelper reflectionHelper() {
        return new ReflectionHelper();
    }

    @Bean
    public RandomUtil randomUtil() {
        return new RandomUtil();
    }
}
