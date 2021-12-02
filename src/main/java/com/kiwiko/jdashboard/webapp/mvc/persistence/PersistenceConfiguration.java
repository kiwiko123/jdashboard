package com.kiwiko.jdashboard.webapp.mvc.persistence;

import com.kiwiko.jdashboard.webapp.mvc.persistence.impl.VersionConverterHelper;
import com.kiwiko.jdashboard.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.mvc.persistence.transactions.internal.SpringTransactionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public VersionConverterHelper versionConverterHelper() {
        return new VersionConverterHelper();
    }

    @Bean
    public TransactionProvider transactionProvider() {
        return new SpringTransactionProvider();
    }
}
