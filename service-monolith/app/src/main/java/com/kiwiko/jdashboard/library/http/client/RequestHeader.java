package com.kiwiko.jdashboard.library.http.client;

import lombok.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Value
public class RequestHeader {
    @Nonnull String name;
    @Nullable String value;
}
