package com.kiwiko.jdashboard.tools.apiclient;

import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@NoArgsConstructor
@Data
public abstract class HttpApiRequest {
    private @Nonnull RequestMethod requestMethod;
    private @Nonnull RequestUrl requestUrl;
    private @Nullable Object requestBody;
    private @Nonnull Set<RequestHeader> requestHeaders;
}
