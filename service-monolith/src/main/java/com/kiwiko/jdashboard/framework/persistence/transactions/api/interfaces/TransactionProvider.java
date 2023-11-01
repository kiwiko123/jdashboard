package com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces;

import java.util.function.Supplier;

public interface TransactionProvider extends SimpleTransactionProvider {

    void readOnly(String dataSource, Runnable runnable);
    <T> T readOnly(String dataSource, Supplier<T> supplier);

    void readWrite(String dataSource, Runnable runnable);
    <T> T readWrite(String dataSource, Supplier<T> supplier);
}
