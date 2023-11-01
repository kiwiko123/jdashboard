package com.kiwiko.jdashboard.library.http.url;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryParameter {
    public static QueryParameter withRawValue(String name, String rawValue) {
        return new QueryParameter(name, rawValue);
    }

    public static QueryParameter withEncodedValue(String name, String valueToEncode) {
        String encodedValue = URLEncoder.encode(valueToEncode, StandardCharsets.UTF_8);
        return new QueryParameter(name, encodedValue);
    }

    private final String name;
    private final String value;

    public QueryParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "QueryParameter{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
