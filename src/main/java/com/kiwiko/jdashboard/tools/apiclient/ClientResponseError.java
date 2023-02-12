package com.kiwiko.jdashboard.tools.apiclient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
@Getter
@ToString
public class ClientResponseError {
    private final @Nonnull String code;
    private final @Nonnull String message;
}
