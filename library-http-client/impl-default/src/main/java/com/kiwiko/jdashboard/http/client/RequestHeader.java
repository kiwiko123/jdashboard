package com.kiwiko.jdashboard.http.client;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Builder
@Value
public class RequestHeader {
    @Nonnull String name;
    @Nullable String value;
}
