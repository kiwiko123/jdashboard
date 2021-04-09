package com.kiwiko.webapp.mvc.lifecycle.startup.internal;

import com.kiwiko.Application;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;
import com.kiwiko.webapp.mvc.lifecycle.startup.api.ClassProcessor;
import com.kiwiko.webapp.mvc.lifecycle.startup.api.errors.LifecycleException;
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
        T processor = new InjectManuallyConfigurer<T>()
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(createClassProcessor(classProcessorType))
                .create();
        classProcessors.add(processor);
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
            classProcessors.forEach(classProcessor -> classProcessor.process(clazz));
        }
    }

    private <T extends ClassProcessor> T createClassProcessor(Class<T> clazz) throws LifecycleException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new LifecycleException(String.format("Failed to create ClassProcessor instance for %s", clazz.getName()), e);
        }
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
        String packageName = getClass().getPackageName(); // Whitelist all classes in ClassScanner's package
        Set<Class<?>> result = provider.findCandidateComponents(packageName).stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::toClass)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        result.add(Application.class);

        return result;
    }
}
