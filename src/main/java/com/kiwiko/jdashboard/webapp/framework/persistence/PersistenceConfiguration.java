package com.kiwiko.jdashboard.webapp.framework.persistence;

import com.kiwiko.jdashboard.webapp.framework.persistence.impl.VersionConverterHelper;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.internal.SpringTransactionProvider;
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
