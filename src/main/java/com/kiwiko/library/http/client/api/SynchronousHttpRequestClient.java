package com.kiwiko.library.http.client.api;

import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;

public interface SynchronousHttpRequestClient {

    <T> HttpClientResponse<T> get(GetRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> post(PostRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> put(PutRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> delete(DeleteRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;
}
