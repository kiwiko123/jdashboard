package com.kiwiko.jdashboard.services.usercredentials.api.interfaces;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

public class QueryUserCredentialsInput {

    public static Builder newBuilder() {
        return new Builder();
    }

    private Set<Long> userIds;
    private Set<String> credentialTypes;
    private @Nullable Boolean isRemoved;

    private QueryUserCredentialsInput() { }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public Set<String> getCredentialTypes() {
        return credentialTypes;
    }

    @Nullable
    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public static final class Builder {
        private Set<Long> userIds;
        private Set<String> credentialTypes;
        private @Nullable Boolean isRemoved;

        public Builder setUserIds(Collection<Long> userIds) {
            this.userIds = Set.copyOf(userIds);
            return this;
        }

        public Builder setCredentialTypes(Collection<String> credentialTypes) {
            this.credentialTypes = Set.copyOf(credentialTypes);
            return this;
        }

        public Builder setIsRemoved(@Nullable Boolean isRemoved) {
            this.isRemoved = isRemoved;
            return this;
        }

        public QueryUserCredentialsInput build() {
            QueryUserCredentialsInput input = new QueryUserCredentialsInput();
            input.userIds = userIds;
            input.credentialTypes = credentialTypes;
            input.isRemoved = isRemoved;

            return input;
        }
    }
}
