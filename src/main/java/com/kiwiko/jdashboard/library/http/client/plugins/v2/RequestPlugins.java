package com.kiwiko.jdashboard.library.http.client.plugins.v2;

import lombok.Value;

import javax.annotation.Nonnull;
import java.util.List;

@Value
public class RequestPlugins<Plugin extends ApiClientRequestPlugin> {
    @SafeVarargs
    public static <T extends ApiClientRequestPlugin> RequestPlugins<T> of(T... plugins) {
        return new RequestPlugins<>(List.of(plugins));
    }

    @Nonnull List<Plugin> plugins;
}
