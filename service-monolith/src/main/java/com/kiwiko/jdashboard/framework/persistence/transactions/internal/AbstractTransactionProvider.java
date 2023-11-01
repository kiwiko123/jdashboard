package com.kiwiko.jdashboard.framework.persistence.transactions.internal;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.SimpleTransactionProvider;

import java.util.function.Supplier;

abstract class AbstractTransactionProvider implements SimpleTransactionProvider {

    @Override
    public void readOnly(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <T> T readOnly(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public void readWrite(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <T> T readWrite(Supplier<T> supplier) {
        return supplier.get();
    }
}
