package com.kiwiko.webapp.mvc;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.caching.impl.InMemoryObjectCache;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;
import com.kiwiko.webapp.mvc.json.impl.GsonJsonSerializer;
import com.kiwiko.webapp.mvc.json.impl.resolvers.CustomRequestBodyResolver;
import com.kiwiko.webapp.metrics.api.annotations.CaptureMetrics;
import com.kiwiko.webapp.mvc.configuration.ConfigurationHelper;
import com.kiwiko.webapp.mvc.interceptors.AuthenticationRequiredInterceptor;
import com.kiwiko.webapp.mvc.interceptors.CaptureMetricsMethodInterceptor;
import com.kiwiko.webapp.mvc.interceptors.internal.SessionRequestHelper;
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
    public AuthenticationRequiredInterceptor authenticationRequiredInterceptor() {
        return new AuthenticationRequiredInterceptor();
    }

    @Bean
    public CaptureMetricsMethodInterceptor captureMetricsMethodInterceptor() {
        return new CaptureMetricsMethodInterceptor();
    }

    @Bean
    public Advisor captureMetricsAdvisor() {
        return configurationHelper.createAnnotationBean(CaptureMetrics.class, captureMetricsMethodInterceptor());
    }
}
