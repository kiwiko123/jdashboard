package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
class RequestKeyEventMetadata {
    private @NonNull String scope;
    private @NonNull String description;
    private String url;
}
