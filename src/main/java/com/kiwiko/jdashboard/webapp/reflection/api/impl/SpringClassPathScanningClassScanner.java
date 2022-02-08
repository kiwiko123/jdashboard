package com.kiwiko.jdashboard.webapp.reflection.api.impl;

import com.kiwiko.jdashboard.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SpringClassPathScanningClassScanner implements ClassScanner {

    @Inject private Logger logger;

    @Override
    public Set<Class<?>> getClasses(String basePackage) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

        return provider.findCandidateComponents(basePackage).stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::toClass)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Nullable
    private Class<?> toClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error(String.format("Unable to process class %s", className), e);
        }
        return null;
    }
}
