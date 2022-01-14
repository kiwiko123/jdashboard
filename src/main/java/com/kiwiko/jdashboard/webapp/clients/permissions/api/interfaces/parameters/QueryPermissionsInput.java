package com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters;

import javax.annotation.Nullable;
import java.util.Set;

public class QueryPermissionsInput {
    public static Builder newBuilder() {
        return new Builder();
    }

    private @Nullable Set<String> permissionNames;
    private @Nullable Set<Long> userIds;

    @Nullable
    public Set<String> getPermissionNames() {
        return permissionNames;
    }

    private void setPermissionNames(@Nullable Set<String> permissionNames) {
        this.permissionNames = permissionNames;
    }

    @Nullable
    public Set<Long> getUserIds() {
        return userIds;
    }

    private void setUserIds(@Nullable Set<Long> userIds) {
        this.userIds = userIds;
    }

    public static class Builder {
        private @Nullable Set<String> permissionNames;
        private @Nullable Set<Long> userIds;

        public Builder setPermissionNames(@Nullable Set<String> permissionNames) {
            this.permissionNames = permissionNames;
            return this;
        }

        public Builder setUserIds(@Nullable Set<Long> userIds) {
            this.userIds = userIds;
            return this;
        }

        public QueryPermissionsInput build() {
            QueryPermissionsInput input = new QueryPermissionsInput();
            input.setPermissionNames(permissionNames);
            input.setUserIds(userIds);
            return input;
        }
    }
}
