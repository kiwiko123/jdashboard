package com.kiwiko.jdashboard.framework.persistence.transactions.internal;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.SimpleTransactionProvider;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.UnknownDataSourceTransactionProviderException;

import javax.inject.Inject;

public class TransactionProviderResolver {

    @Inject private JdashboardDefaultTransactionProvider defaultTransactionProvider;
    @Inject private FrameworkInternalTransactionProvider frameworkInternalTransactionProvider;

    public SimpleTransactionProvider byDataSource(String dataSource) {
        if (JdashboardDataSources.DEFAULT.equals(dataSource)) {
            return defaultTransactionProvider;
        }

        if (JdashboardDataSources.FRAMEWORK_INTERNAL.equals(dataSource)) {
            return frameworkInternalTransactionProvider;
        }

        throw new UnknownDataSourceTransactionProviderException(String.format("Cannot provision database transaction. Unknown data source: %s", dataSource));
    }
}
