package com.kiwiko.jdashboard.framework.persistence.transactions.internal;

import com.kiwiko.jdashboard.framework.datasources.DataSourceBeanNames;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class FrameworkInternalTransactionProvider extends AbstractTransactionProvider {

    @Override
    @Transactional(transactionManager = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL, readOnly = true)
    public void readOnly(Runnable runnable) {
        super.readOnly(runnable);
    }

    @Override
    @Transactional(transactionManager = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL, readOnly = true)
    public <T> T readOnly(Supplier<T> supplier) {
        return super.readOnly(supplier);
    }

    @Override
    @Transactional(transactionManager = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL)
    public void readWrite(Runnable runnable) {
        super.readWrite(runnable);
    }

    @Override
    @Transactional(value = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL)
    public <T> T readWrite(Supplier<T> supplier) {
        return super.readWrite(supplier);
    }
}
