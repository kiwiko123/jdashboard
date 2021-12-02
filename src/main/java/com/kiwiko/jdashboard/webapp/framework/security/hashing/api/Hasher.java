package com.kiwiko.jdashboard.webapp.framework.security.hashing.api;

public interface Hasher<T> {

    long hash(T value);
}
