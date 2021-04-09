package com.kiwiko.webapp.mvc;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.caching.impl.InMemoryObjectCache;
import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;
import com.kiwiko.webapp.mvc.json.impl.GsonJsonSerializer;
import com.kiwiko.webapp.mvc.json.impl.resolvers.CustomRequestBodyResolver;
import com.kiwiko.webapp.mvc.performance.api.annotations.Throttle;
import com.kiwiko.webapp.metrics.api.annotations.CaptureMetrics;
import com.kiwiko.webapp.mvc.configuration.ConfigurationHelper;
import com.kiwiko.webapp.mvc.interceptors.AuthenticationRequiredInterceptor;
import com.kiwiko.webapp.mvc.interceptors.CaptureMetricsMethodInterceptor;
import com.kiwiko.webapp.mvc.interceptors.RequestContextInterceptor;
import com.kiwiko.webapp.mvc.interceptors.RequestErrorInterceptor;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
import com.kiwiko.webapp.mvc.interceptors.ThrottleMethodInterceptor;
import com.kiwiko.webapp.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;
import com.kiwiko.webapp.mvc.requests.internal.InMemoryRequestContextService;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.resolvers.RequestBodyCollectionParameterResolver;
import com.kiwiko.webapp.mvc.resolvers.RequestBodyParameterResolver;
import com.kiwiko.webapp.mvc.resolvers.RequestContextResolver;
import com.kiwiko.webapp.mvc.security.environments.api.EnvironmentService;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.mvc.security.environments.internal.WebApplicationEnvironmentService;
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
        resolvers.add(customRequestBodyResolver());
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
    public ObjectCache objectCache() {
        return new InMemoryObjectCache();
    }

    @Bean
    public JsonMapper propertyObjectMapper() {
        return new JsonMapper();
    }

    @Bean
    public JsonSerializer jsonSerializer() {
        return new GsonJsonSerializer();
    }

    @Bean
    public GsonJsonSerializer gsonJsonSerializer() {
        return new GsonJsonSerializer();
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
    public Logger logger() {
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
    public CustomRequestBodyResolver customRequestBodyResolver() {
        return new CustomRequestBodyResolver();
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
                .withBinding(ObjectCache.class, InMemoryObjectCache.class)
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(new ThrottleMethodInterceptor())
                .create();
        return configurationHelper.createAnnotationBean(Throttle.class, instance);
    }
}
