package com.kiwiko.mvc.lifecycle.startup.internal;

import com.kiwiko.Application;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import com.kiwiko.mvc.lifecycle.startup.api.ClassProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClassScanner {

    private final Set<ClassProcessor> classProcessors;

    @InjectManually
    private LogService logService;

    public ClassScanner() {
        classProcessors = new HashSet<>();
    }

    public <T extends ClassProcessor> ClassScanner register(Class<T> classProcessorType) {
        createClassProcessor(classProcessorType)
                .ifPresent(classProcessors::add);
        return this;
    }

    public Set<Class<?>> scan() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        Set<Class<?>> whitelistedClasses = getWhitelistedClasses(provider);
        return provider.findCandidateComponents("com.kiwiko").stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::toClass)
                .flatMap(Optional::stream)
                .filter(clazz -> !whitelistedClasses.contains(clazz))
                .collect(Collectors.toSet());
    }

    public void process() {
        Set<Class<?>> allClasses = scan();
        for (Class<?> clazz : allClasses) {
            classProcessors.stream()
                    .forEach(classProcessor -> classProcessor.process(clazz));
        }
    }

    private <T extends ClassProcessor> Optional<T> createClassProcessor(Class<T> clazz) {
        T result = null;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logService.error(String.format("Failed to create ClassProcessor instance for %s", clazz.getName()), e);
        }

        return Optional.ofNullable(result);
    }

    private Optional<Class<?>> toClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logService.error(String.format("Unable to process class %s", className), e);
        }
        return Optional.ofNullable(clazz);
    }

    private Set<Class<?>> getWhitelistedClasses(ClassPathScanningCandidateComponentProvider provider) {
        Set<Class<?>> result = new HashSet<>();
        result.add(Application.class);

        String packageName = getClass().getPackageName();
        provider.findCandidateComponents(packageName).stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::toClass)
                .flatMap(Optional::stream)
                .forEach(result::add);

        return result;
    }
}
