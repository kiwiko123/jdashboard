package com.kiwiko.webapp.flags.features.dto;

import com.kiwiko.library.persistence.dataAccess.data.VersionedEntityDTO;

import javax.annotation.Nullable;

public class FeatureFlag extends VersionedEntityDTO {

    public static FeatureFlag empty() {
        FeatureFlag result = new FeatureFlag();
        result.setStatus(FeatureFlagStatus.DISABLED.getId());
        result.setUserScope(FeatureFlagUserScope.PUBLIC.getId());
        return result;
    }

    private String name;
    private String status;
    private @Nullable String value;
    private String userScope;
    private @Nullable Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public String getUserScope() {
        return userScope;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }
}
