package com.kiwiko.mvc;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.memory.caching.internal.InMemoryCacheService;
import com.kiwiko.memory.performance.api.annotations.Throttle;
import com.kiwiko.metrics.api.CaptureMetrics;
import com.kiwiko.mvc.configuration.ConfigurationHelper;
import com.kiwiko.mvc.interceptors.AuthenticationRequiredInterceptor;
import com.kiwiko.mvc.interceptors.CaptureMetricsMethodInterceptor;
import com.kiwiko.mvc.interceptors.RequestContextInterceptor;
import com.kiwiko.mvc.interceptors.RequestErrorInterceptor;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.impl.ConsoleLogService;
import com.kiwiko.mvc.interceptors.ThrottleMethodInterceptor;
import com.kiwiko.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;
import com.kiwiko.mvc.requests.internal.InMemoryRequestContextService;
import com.kiwiko.mvc.json.api.JsonMapper;
import com.kiwiko.mvc.resolvers.RequestBodyCollectionParameterResolver;
import com.kiwiko.mvc.resolvers.RequestBodyParameterResolver;
import com.kiwiko.mvc.resolvers.RequestContextResolver;
import com.kiwiko.mvc.security.environments.api.EnvironmentService;
import com.kiwiko.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.mvc.security.environments.internal.WebApplicationEnvironmentService;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
        registry.addInterceptor(authenticationRequiredInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(EnvironmentProperties.CROSS_ORIGIN_URL);
    }

    @Bean
    public CacheService cacheService() {
        return new InMemoryCacheService();
    }

    @Bean
    public JsonMapper propertyObjectMapper() {
        return new JsonMapper();
    }

    @Bean
    public InMemoryRequestContextService ineMemoryRequestContextService() {
        return new InMemoryRequestContextService();
    }

    @Bean
    public EnvironmentService environmentService() {
        return new WebApplicationEnvironmentService();
    }

    @Bean
    public LogService logService() {
        return new ConsoleLogService();
    }

    @Bean
    public SessionRequestHelper sessionRequestHelper() {
        return new SessionRequestHelper();
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
    public AuthenticationRequiredInterceptor authenticationRequiredInterceptor() {
        return new AuthenticationRequiredInterceptor();
    }

    @Bean
    public Advisor captureMetricsAdvisor() {
        CaptureMetricsMethodInterceptor instance = new InjectManuallyConfigurer<CaptureMetricsMethodInterceptor>()
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(new CaptureMetricsMethodInterceptor())
                .create();
        return configurationHelper.createAnnotationBean(CaptureMetrics.class, instance);
    }

    @Bean
    public Advisor throttleAdvisor() {
        ThrottleMethodInterceptor instance = new InjectManuallyConfigurer<ThrottleMethodInterceptor>()
                .withBinding(CacheService.class, InMemoryCacheService.class)
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(new ThrottleMethodInterceptor())
                .create();
        return configurationHelper.createAnnotationBean(Throttle.class, instance);
    }
}
