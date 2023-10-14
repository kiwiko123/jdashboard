package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.RequestPlugins;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.ServiceRequestKeyProvisioningPreRequestPlugin;

import javax.inject.Inject;

public class DefaultJdashboardApiClientPlugins {
    @Inject private RequestLoggerPreRequestPlugin requestLoggerPreRequestPlugin;
    @Inject private ServiceRequestKeyProvisioningPreRequestPlugin serviceRequestKeyProvisioningPreRequestPlugin;

    public RequestPlugins<PreRequestPlugin> getPreRequestPlugins() {
        return RequestPlugins.of(
                serviceRequestKeyProvisioningPreRequestPlugin,
                requestLoggerPreRequestPlugin);
    }

    public RequestPlugins<PostRequestPlugin> getPostRequestPlugins() {
        return RequestPlugins.of();
    }
}
