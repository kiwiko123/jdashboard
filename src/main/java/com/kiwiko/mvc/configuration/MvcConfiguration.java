package com.kiwiko.mvc.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.memory.caching.internal.InMemoryCacheService;
import com.kiwiko.metrics.api.CaptureMetrics;
import com.kiwiko.mvc.interceptors.CaptureMetricsMethodInterceptor;
import com.kiwiko.mvc.interceptors.RequestContextInterceptor;
import com.kiwiko.mvc.interceptors.RequestErrorInterceptor;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.internal.ConsoleLogService;
import com.kiwiko.mvc.lifecycle.dependencies.data.DependencyBinding;
import com.kiwiko.mvc.requests.api.RequestContextService;
import com.kiwiko.mvc.json.PropertyObjectMapper;
import com.kiwiko.mvc.resolvers.RequestBodyCollectionParameterResolver;
import com.kiwiko.mvc.resolvers.RequestBodyParameterResolver;
import com.kiwiko.mvc.resolvers.RequestContextResolver;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private ConfigurationHelper configurationHelper;

    public MvcConfiguration() {
        configurationHelper = new ConfigurationHelper();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestBodyParameterResolver());
        resolvers.add(requestBodyCollectionParameterResolver());
        resolvers.add(requestContextResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(requestErrorInterceptor());
    }

    @Bean
    public CacheService cacheService() {
        return new InMemoryCacheService();
    }

    @Bean
    public PropertyObjectMapper propertyObjectMapper() {
        return new PropertyObjectMapper();
    }

    @Bean
    public RequestContextService requestContextService() {
        return new RequestContextService();
    }

    @Bean
    public LogService logService() {
        return new ConsoleLogService();
    }

    @Bean
    public RequestBodyParameterResolver requestBodyParameterResolver() {
        return new RequestBodyParameterResolver();
    }

    @Bean
    public RequestBodyCollectionParameterResolver requestBodyCollectionParameterResolver() {
        return new RequestBodyCollectionParameterResolver();
    }

    @Bean
    public RequestContextResolver requestContextResolver() {
        return new RequestContextResolver();
    }

    @Bean
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Bean
    public RequestErrorInterceptor requestErrorInterceptor() {
        return new RequestErrorInterceptor();
    }

    @Bean
    public Advisor captureMetricsAdvisor() {
        DependencyBinding binding = new DependencyBinding(LogService.class, ConsoleLogService.class);
        CaptureMetricsMethodInterceptor instance = configurationHelper
                .createWithManualInjection(new CaptureMetricsMethodInterceptor(), binding);
        return configurationHelper.createAnnotationBean(CaptureMetrics.class, instance);
    }

    /**
     * Allows public, protected, and private fields to be serialized in a web response.
     */
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
