package com.kiwiko.jdashboard.http.client.url;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Value
public class UrlQuery {
    @Builder.Default
    @NonNull
    List<QueryParameter> queryParameters = List.of();

    public String toQuery() {
        return queryParameters.stream()
                .map(queryParameter -> String.format("%s=%s", queryParameter.getName(), queryParameter.getValue()))
                .collect(Collectors.joining("&"));
    }
}
