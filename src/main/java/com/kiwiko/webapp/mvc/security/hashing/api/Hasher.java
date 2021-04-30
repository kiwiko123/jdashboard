package com.kiwiko.webapp.mvc.security.hashing.api;

public interface Hasher<T> {

    long hash(T value);
}
