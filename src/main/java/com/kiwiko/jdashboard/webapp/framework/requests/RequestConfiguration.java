package com.kiwiko.jdashboard.webapp.framework.requests;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.requests.api.CurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.RequestContextEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.RequestContextEntityService;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.RequestAttributesCurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess.RequestContextEntityDAO;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestErrorInterceptor;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public RequestContextEntityDAO requestContextEntityDAO() {
        return new RequestContextEntityDAO();
    }

    @Bean
    @ConfiguredBy(PersistenceServicesCrudConfiguration.class)
    public RequestContextService requestContextService() {
        return new RequestContextEntityService();
    }

    @Bean
    public RequestContextEntityMapper requestContextEntityPropertyMapper() {
        return new RequestContextEntityMapper();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public CurrentRequestService currentRequestService() {
        return new RequestAttributesCurrentRequestService();
    }

    @Bean
    @ConfiguredBy({LoggingConfiguration.class, MvcConfiguration.class})
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public RequestErrorInterceptor requestErrorInterceptor() {
        return new RequestErrorInterceptor();
    }
}
