package com.kiwiko.mvc.configuration;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.memory.caching.internal.InMemoryCacheService;
import com.kiwiko.mvc.handlers.WebRequestInterceptor;
import com.kiwiko.mvc.requests.api.RequestContextService;
import com.kiwiko.mvc.json.PropertyObjectMapper;
import com.kiwiko.mvc.resolvers.RequestBodyParameterResolver;
import com.kiwiko.mvc.resolvers.RequestContextResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestBodyParameterResolver());
        resolvers.add(requestContextResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webRequestInterceptor());
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
    public RequestBodyParameterResolver requestBodyParameterResolver() {
        return new RequestBodyParameterResolver();
    }

    @Bean
    public RequestContextResolver requestContextResolver() {
        return new RequestContextResolver();
    }

    @Bean
    public WebRequestInterceptor webRequestInterceptor() {
        return new WebRequestInterceptor();
    }
}
