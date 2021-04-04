package com.kiwiko.library.http.client.internal.serialization;

public interface RequestSerializer {

    String toJson(Object value);

    <T> T fromJson(String json, Class<T> responseType);
}
