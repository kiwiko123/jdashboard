package com.kiwiko.jdashboard.framework.persistence.transactions;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.framework.persistence.transactions.internal.SpringTransactionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public TransactionProvider transactionProvider() {
        return new SpringTransactionProvider();
    }
}
