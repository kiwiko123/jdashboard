package com.kiwiko.jdashboard.http.client;

import com.kiwiko.jdashboard.http.client.url.RequestUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleHttpRequest implements HttpRequest {
    @Nonnull RequestMethod requestMethod;
    @Nonnull RequestUrl requestUrl;
    @Nullable Object requestBody;

    @Builder.Default
    @Nonnull Set<RequestHeader> requestHeaders = Set.of();
}
