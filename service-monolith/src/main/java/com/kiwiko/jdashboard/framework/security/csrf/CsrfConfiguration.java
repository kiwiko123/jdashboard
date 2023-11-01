package com.kiwiko.jdashboard.framework.security.csrf;

import com.kiwiko.jdashboard.framework.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsrfConfiguration {

    @Bean
    public CrossSiteRequestForgeryPreventionInterceptor crossSiteRequestForgeryPreventionInterceptor(
            @Value("${jdashboard.framework.security.csrf.allowed-cross-origin-urls}") String[] allowedCrossOriginUrls) {
        return new CrossSiteRequestForgeryPreventionInterceptor(allowedCrossOriginUrls);
    }
}
