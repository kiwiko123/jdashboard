package com.kiwiko.jdashboard.webapp.framework.persistence.transactions.internal;

import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class SpringTransactionProvider implements TransactionProvider {

    @Override
    @Transactional(readOnly = true)
    public void readOnly(Runnable runnable) {
        runnable.run();
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T readOnly(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    @Transactional(readOnly = false)
    public void readWrite(Runnable runnable) {
        runnable.run();
    }

    @Override
    @Transactional(readOnly = false)
    public <T> T readWrite(Supplier<T> supplier) {
        return supplier.get();
    }
}
