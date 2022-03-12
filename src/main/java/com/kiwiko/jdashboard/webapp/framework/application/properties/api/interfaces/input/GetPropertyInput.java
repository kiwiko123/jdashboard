package com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input;

public class GetPropertyInput {
    private final String propertyName;
    private final PropertyCacheControls cacheControls;

    public GetPropertyInput(String propertyName, PropertyCacheControls cacheControls) {
        this.propertyName = propertyName;
        this.cacheControls = cacheControls;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public PropertyCacheControls getCacheControls() {
        return cacheControls;
    }
}
