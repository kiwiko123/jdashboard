package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

import javax.annotation.Nullable;
import java.util.Set;

public class QueryGroceryListsInput {
    private @Nullable Set<Long> ids;
    private @Nullable Set<Long> userIds;
    private @Nullable Set<String> names;
    private @Nullable Boolean isRemoved = false;

    @Nullable
    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(@Nullable Set<Long> ids) {
        this.ids = ids;
    }

    @Nullable
    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(@Nullable Set<Long> userIds) {
        this.userIds = userIds;
    }

    @Nullable
    public Set<String> getNames() {
        return names;
    }

    public void setNames(@Nullable Set<String> names) {
        this.names = names;
    }

    @Nullable
    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(@Nullable Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
