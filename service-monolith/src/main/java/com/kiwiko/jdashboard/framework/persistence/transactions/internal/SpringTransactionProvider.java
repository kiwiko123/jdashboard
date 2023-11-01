package com.kiwiko.jdashboard.framework.persistence.transactions.internal;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;

import javax.inject.Inject;
import java.util.function.Supplier;

public class SpringTransactionProvider extends AbstractTransactionProvider implements TransactionProvider {

    @Inject private TransactionProviderResolver transactionProviderResolver;

    @Override
    public void readOnly(String dataSource, Runnable runnable) {
        transactionProviderResolver.byDataSource(dataSource).readOnly(runnable);
    }

    @Override
    public <T> T readOnly(String dataSource, Supplier<T> supplier) {
        return transactionProviderResolver.byDataSource(dataSource).readOnly(supplier);
    }

    @Override
    public void readWrite(String dataSource, Runnable runnable) {
        transactionProviderResolver.byDataSource(dataSource).readWrite(runnable);
    }

    @Override
    public <T> T readWrite(String dataSource, Supplier<T> supplier) {
        return transactionProviderResolver.byDataSource(dataSource).readWrite(supplier);
    }

    @Override
    public void readOnly(Runnable runnable) {
        readOnly(JdashboardDataSources.DEFAULT, runnable);
    }

    @Override
    public <T> T readOnly(Supplier<T> supplier) {
        return readOnly(JdashboardDataSources.DEFAULT, supplier);
    }

    @Override
    public void readWrite(Runnable runnable) {
        readWrite(JdashboardDataSources.DEFAULT, runnable);
    }

    @Override
    public <T> T readWrite(Supplier<T> supplier) {
        return readWrite(JdashboardDataSources.DEFAULT, supplier);
    }


}
