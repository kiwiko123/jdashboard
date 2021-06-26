package com.kiwiko.webapp.mvc.requests;

import com.kiwiko.webapp.mvc.requests.api.CurrentRequestService;
import com.kiwiko.webapp.mvc.requests.api.RequestContextService;
import com.kiwiko.webapp.mvc.requests.internal.RequestContextEntityMapper;
import com.kiwiko.webapp.mvc.requests.internal.RequestContextEntityService;
import com.kiwiko.webapp.mvc.requests.internal.RequestAttributesCurrentRequestService;
import com.kiwiko.webapp.mvc.requests.internal.dataAccess.RequestContextEntityDAO;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestErrorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestConfiguration {

    @Bean
    public RequestContextEntityDAO requestContextEntityDAO() {
        return new RequestContextEntityDAO();
    }

    @Bean
    public RequestContextService requestContextService() {
        return new RequestContextEntityService();
    }

    @Bean
    public RequestContextEntityMapper requestContextEntityPropertyMapper() {
        return new RequestContextEntityMapper();
    }

    @Bean
    public CurrentRequestService currentRequestService() {
        return new RequestAttributesCurrentRequestService();
    }

    @Bean
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Bean
    public RequestErrorInterceptor requestErrorInterceptor() {
        return new RequestErrorInterceptor();
    }
}
