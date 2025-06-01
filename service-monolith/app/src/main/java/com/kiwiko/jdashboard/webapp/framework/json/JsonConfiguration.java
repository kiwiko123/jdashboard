package com.kiwiko.jdashboard.webapp.framework.json;

import com.kiwiko.jdashboard.library.lang.random.RandomUtil;
import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
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
