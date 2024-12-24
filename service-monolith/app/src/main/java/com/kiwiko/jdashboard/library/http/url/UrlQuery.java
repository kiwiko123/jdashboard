package com.kiwiko.jdashboard.library.http.url;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UrlQuery {
    public static Builder newBuilder() {
        return new Builder();
    }

    private final List<QueryParameter> queryParameters;

    private UrlQuery(List<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public String toQuery() {
        return queryParameters.stream()
                .map(queryParameter -> String.format("%s=%s", queryParameter.getName(), queryParameter.getValue()))
                .collect(Collectors.joining("&"));
    }

    public static class Builder {
        private final List<QueryParameter> queryParameters;

        private Builder() {
            queryParameters = new LinkedList<>();
        }

        public Builder addQueryParameter(QueryParameter queryParameter) {
            queryParameters.add(queryParameter);
            return this;
        }

        public UrlQuery build() {
            return new UrlQuery(queryParameters);
        }
    }

    @Override
    public String toString() {
        return "UrlQuery{" +
                "queryParameters=" + queryParameters +
                '}';
    }
}
