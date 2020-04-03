package com.kiwiko.lang;

import com.kiwiko.lang.reflection.ReflectionHelper;
import com.kiwiko.lang.types.TypesHelper;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.impl.ConsoleLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangConfiguration {

    @Bean
    public ReflectionHelper inheritanceReflectionHelper() {
        return new ReflectionHelper();
    }

    @Bean
    public TypesHelper typesHelper() {
        return new TypesHelper();
    }

    @Bean
    public LogService logService() {
        return new ConsoleLogService();
    }
}
