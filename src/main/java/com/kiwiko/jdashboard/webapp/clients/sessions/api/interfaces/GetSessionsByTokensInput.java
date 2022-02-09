package com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GetSessionsByTokensInput {
    private Set<String> tokens;

    public GetSessionsByTokensInput(Collection<String> tokens) {
        this.tokens = new HashSet<>(tokens);
    }

    public Set<String> getTokens() {
        return tokens;
    }
}
