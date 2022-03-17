package com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ApiClientPlugins {
    private final List<PreRequestPlugin> preRequestPlugins;
    private final List<PostRequestPlugin> postRequestPlugins;

    public ApiClientPlugins(Collection<PreRequestPlugin> preRequestPlugins, Collection<PostRequestPlugin> postRequestPlugins) {
        this.preRequestPlugins = List.copyOf(preRequestPlugins);
        this.postRequestPlugins = List.copyOf(postRequestPlugins);
    }

    public ApiClientPlugins() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public List<PreRequestPlugin> getPreRequestPlugins() {
        return preRequestPlugins;
    }

    public List<PostRequestPlugin> getPostRequestPlugins() {
        return postRequestPlugins;
    }
}
