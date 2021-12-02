package com.kiwiko.jdashboard.webapp.mvc.security.hashing.api;

public abstract class MultiplicationHasher implements Hasher<Long> {

    protected abstract double tableSize();

    protected abstract double constant();

    @Override
    public long hash(Long value) {
        double m = tableSize();
        double c = constant();
        return (long) Math.floor(m * (value * (c % 1)));
    }
}
