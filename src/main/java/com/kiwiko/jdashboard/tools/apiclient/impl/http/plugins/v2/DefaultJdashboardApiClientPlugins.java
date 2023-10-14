package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.RequestPlugins;

import javax.inject.Inject;
import java.util.List;

public class DefaultJdashboardApiClientPlugins {
    @Inject private RequestLoggerPreRequestPlugin requestLoggerPreRequestPlugin;

    public RequestPlugins<PreRequestPlugin> getPreRequestPlugins() {
        List<PreRequestPlugin> plugins = List.of(requestLoggerPreRequestPlugin);
        return new RequestPlugins<>(plugins);
    }

    public RequestPlugins<PostRequestPlugin> getPostRequestPlugins() {
        List<PostRequestPlugin> plugins = List.of();
        return new RequestPlugins<>(plugins);
    }
}
