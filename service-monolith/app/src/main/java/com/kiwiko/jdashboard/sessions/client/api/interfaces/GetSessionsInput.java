package com.kiwiko.jdashboard.sessions.client.api.interfaces;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class GetSessionsInput {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final @Nullable Set<Long> sessionIds;
    private final @Nullable Set<String> tokens;
    private final @Nullable Boolean isActive;

    public GetSessionsInput(
            @Nullable Set<Long> sessionIds,
            @Nullable Set<String> tokens,
            @Nullable Boolean isActive) {
        this.sessionIds = sessionIds;
        this.tokens = tokens;
        this.isActive = isActive;
    }

    @Nullable
    public Set<Long> getSessionIds() {
        return sessionIds;
    }

    @Nullable
    public Set<String> getTokens() {
        return tokens;
    }

    @Nullable
    public Boolean getIsActive() {
        return isActive;
    }

    public static final class Builder {
        private Set<Long> sessionIds;
        private Set<String> tokens;
        private Boolean isActive;

        public Builder setSessionIds(Collection<Long> sessionIds) {
            this.sessionIds = Collections.unmodifiableSortedSet(new TreeSet<>(sessionIds));
            return this;
        }

        public Builder setTokens(Collection<String> tokens) {
            this.tokens = Collections.unmodifiableSortedSet(new TreeSet<>(tokens));
            return this;
        }

        public Builder setIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public GetSessionsInput build() {
            return new GetSessionsInput(sessionIds, tokens, isActive);
        }
    }
}
