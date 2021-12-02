package com.kiwiko.jdashboard.webapp.mvc.persistence.transactions.api.interfaces;

import java.util.function.Supplier;

public interface TransactionProvider {

    void readOnly(Runnable runnable);
    <T> T readOnly(Supplier<T> supplier);

    void readWrite(Runnable runnable);
    <T> T readWrite(Supplier<T> supplier);
}
