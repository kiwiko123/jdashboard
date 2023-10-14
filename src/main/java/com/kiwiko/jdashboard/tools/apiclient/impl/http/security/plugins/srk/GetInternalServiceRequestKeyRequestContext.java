package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk;

import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

import javax.annotation.Nonnull;
import java.time.Duration;

public class GetInternalServiceRequestKeyRequestContext extends HttpApiRequestContext<GetInternalServiceRequestKeyRequest> {
    public GetInternalServiceRequestKeyRequestContext(@Nonnull GetInternalServiceRequestKeyRequest request) {
        super(request);

        setRequestTimeout(Duration.ofSeconds(1));
        setResponseType(ProvisionServiceRequestKeyOutput.class);
    }
}
