package com.kiwiko.library.http.client.internal;

import com.kiwiko.library.http.client.api.errors.ClientException;

import java.net.http.HttpRequest;

@FunctionalInterface
interface RequestConverter {

    HttpRequest get() throws ClientException;
}
