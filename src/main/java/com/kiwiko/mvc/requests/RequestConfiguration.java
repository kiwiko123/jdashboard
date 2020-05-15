package com.kiwiko.mvc.requests;

import com.kiwiko.mvc.requests.api.RequestContextService;
import com.kiwiko.mvc.requests.internal.RequestContextEntityMapper;
import com.kiwiko.mvc.requests.internal.RequestContextEntityService;
import com.kiwiko.mvc.requests.internal.dataAccess.RequestContextEntityDAO;
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
}
