package com.kiwiko.jdashboard.framework.persistence.transactions.internal;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class JdashboardDefaultTransactionProvider extends AbstractTransactionProvider {

    @Override
    @Transactional(readOnly = true)
    public void readOnly(Runnable runnable) {
        super.readOnly(runnable);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T readOnly(Supplier<T> supplier) {
        return super.readOnly(supplier);
    }

    @Override
    @Transactional
    public void readWrite(Runnable runnable) {
        super.readWrite(runnable);
    }

    @Override
    @Transactional
    public <T> T readWrite(Supplier<T> supplier) {
        return super.readWrite(supplier);
    }
}
