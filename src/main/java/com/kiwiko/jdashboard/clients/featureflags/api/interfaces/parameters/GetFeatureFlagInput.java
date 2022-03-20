package com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters;

import javax.annotation.Nullable;

public class GetFeatureFlagInput {
    public static Builder newBuilder() {
        return new Builder();
    }

    private @Nullable Long featureFlagId;
    private @Nullable String featureFlagName;
    private @Nullable Long userId;

    @Nullable
    public Long getFeatureFlagId() {
        return featureFlagId;
    }

    private void setFeatureFlagId(@Nullable Long featureFlagId) {
        this.featureFlagId = featureFlagId;
    }

    @Nullable
    public String getFeatureFlagName() {
        return featureFlagName;
    }

    private void setFeatureFlagName(@Nullable String featureFlagName) {
        this.featureFlagName = featureFlagName;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    private void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    public static final class Builder {
        private Long featureFlagId;
        private String featureFlagName;
        private Long userId;

        public Builder getFeatureFlagId(Long featureFlagId) {
            this.featureFlagId = featureFlagId;
            return this;
        }

        public Builder getFeatureFlagName(String featureFlagName) {
            this.featureFlagName = featureFlagName;
            return this;
        }

        public Builder getUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public GetFeatureFlagInput build() {
            GetFeatureFlagInput input = new GetFeatureFlagInput();
            input.setFeatureFlagId(featureFlagId);
            input.setFeatureFlagName(featureFlagName);
            input.setUserId(userId);
            return input;
        }
    }
}
