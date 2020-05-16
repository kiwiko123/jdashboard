package com.kiwiko.library.lang;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.lang.types.TypesHelper;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
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
