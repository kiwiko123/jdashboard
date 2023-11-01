package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk;

import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;

public class GetInternalServiceRequestKeyRequestContext extends HttpApiRequestContext<GetInternalServiceRequestKeyRequest> {

    public GetInternalServiceRequestKeyRequestContext(@Nonnull GetInternalServiceRequestKeyRequest request) {
        super(request);
    }

    @Nullable
    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(1);
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return ProvisionServiceRequestKeyOutput.class;
    }
}
