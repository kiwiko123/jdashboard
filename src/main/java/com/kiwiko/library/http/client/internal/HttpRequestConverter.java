package com.kiwiko.library.http.client.internal;

import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;
import com.kiwiko.library.http.client.internal.serialization.RequestSerializer;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

class HttpRequestConverter {

    public HttpRequest convertGetRequest(GetRequest request) throws ClientException {
        return convertBaseRequest(request)
                .GET()
                .build();
    }

    public HttpRequest convertPostRequest(PostRequest request, RequestSerializer serializer) throws ClientException {
        return convertBaseRequest(request)
                .POST(makeBodyPublisher(request.getBody(), serializer))
                .build();
    }

    public HttpRequest convertPutRequest(PutRequest request, RequestSerializer serializer) throws ClientException {
        return convertBaseRequest(request)
                .PUT(makeBodyPublisher(request, serializer))
                .build();
    }

    public HttpRequest convertDeleteRequest(DeleteRequest request) throws ClientException {
        return convertBaseRequest(request)
                .DELETE()
                .build();
    }

    public HttpRequest.Builder convertBaseRequest(HttpClientRequest request) throws ClientException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(makeUri(request.getUrl()))
                .timeout(request.getTimeout());

        request.getRequestHeaders().forEach(header -> builder.header(header.getName(), header.getValue()));

        return builder;
    }

    private URI makeUri(String url) throws ClientException {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new ClientException(String.format("Failed to create URI from %s", url), e);
        }
    }

    private HttpRequest.BodyPublisher makeBodyPublisher(Object body, RequestSerializer serializer) {
        String bodyString = serializer.toJson(body);
        return HttpRequest.BodyPublishers.ofString(bodyString);
    }
}
