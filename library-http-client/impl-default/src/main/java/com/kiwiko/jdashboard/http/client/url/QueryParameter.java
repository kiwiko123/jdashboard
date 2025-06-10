package com.kiwiko.jdashboard.http.client.url;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class QueryParameter {
    public static QueryParameter withRawValue(String name, String rawValue) {
        return new QueryParameter(name, rawValue);
    }

    public static QueryParameter withUrlEncodedValue(String name, String valueToEncode) {
        String encodedValue = URLEncoder.encode(valueToEncode, StandardCharsets.UTF_8);
        return new QueryParameter(name, encodedValue);
    }

    @NonNull String name;
    @NonNull String value;
}
