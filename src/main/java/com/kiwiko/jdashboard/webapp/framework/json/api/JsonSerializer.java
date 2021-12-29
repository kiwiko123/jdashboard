package com.kiwiko.jdashboard.webapp.framework.json.api;

public interface JsonSerializer {

    String toJson(Object value);

    <T> T fromJson(String json, Class<T> responseType);
}
