package com.kiwiko.webapp.featureflags.internal.events;

class FeatureFlagEventConstants {
    public static final String EVENT_TYPE = "__JDASHBOARD_ADMIN_FEATURE_FLAGS";
    public static final String CREATE_FEATURE_FLAG_EVENT_TYPE = String.format("%s--create-feature-flag", EVENT_TYPE);
    public static final String UPDATE_FEATURE_FLAG_EVENT_TYPE = String.format("%s--update-feature-flag", EVENT_TYPE);
    public static final String MERGE_FEATURE_FLAG_EVENT_TYPE = String.format("%s--merge-feature-flag", EVENT_TYPE);
    public static final String DELETE_FEATURE_FLAG_EVENT_TYPE = String.format("%s--delete-feature-flag", EVENT_TYPE);
}
