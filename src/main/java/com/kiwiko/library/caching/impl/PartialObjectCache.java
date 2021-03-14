package com.kiwiko.library.caching.impl;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.lang.random.TokenGenerator;

abstract class PartialObjectCache implements ObjectCache {
    private final TokenGenerator tokenGenerator;

    protected PartialObjectCache() {
        tokenGenerator = new TokenGenerator();
    }

    @Override
    public String generateKey() {
        String key = "";
        int tries = 0;
        do {
            key = tokenGenerator.generateToken(24);
        } while (++tries < 10 && get(key).isEmpty());

        return key;
    }
}
