package com.kiwiko.jdashboard.webapp.clients.users.api.parameters;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GetUsersBulkQuery {

    public static Builder newBuilder() {
        return new Builder();
    }

    private @Nullable Set<Long> userIds;
    private @Nullable Set<String> usernames;

    @Nullable
    public Set<Long> getUserIds() {
        return userIds;
    }

    private void setUserIds(@Nullable Set<Long> userIds) {
        this.userIds = userIds;
    }

    @Nullable
    public Set<String> getUsernames() {
        return usernames;
    }

    private void setUsernames(@Nullable Set<String> usernames) {
        this.usernames = usernames;
    }

    public static class Builder {
        private @Nullable Set<Long> userIds;
        private @Nullable Set<String> usernames;

        public Builder setUserIds(Collection<Long> userIds) {
            this.userIds = new HashSet<>(userIds);
            return this;
        }

        public Builder setUsernames(Collection<String> usernames) {
            this.usernames = new HashSet<>(usernames);
            return this;
        }

        public GetUsersBulkQuery build() {
            GetUsersBulkQuery query = new GetUsersBulkQuery();
            query.setUserIds(userIds);
            query.setUsernames(usernames);

            return query;
        }
    }
}
