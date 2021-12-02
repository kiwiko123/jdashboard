package com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.internal;

import com.kiwiko.jdashboard.webapp.mvc.security.hashing.api.MultiplicationHasher;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

class JdashboardInternalRequestHasher extends MultiplicationHasher {

    @Override
    protected double tableSize() {
        return Math.pow(2, 14);
    }

    @Override
    protected double constant() {
        return (Math.sqrt(5) - 1) / 2;
    }

    public long hash(String url) {
        byte[] bytes = url.getBytes(StandardCharsets.UTF_8);
        long value = ByteBuffer.wrap(bytes).getLong();
        return hash(value);
    }
}
