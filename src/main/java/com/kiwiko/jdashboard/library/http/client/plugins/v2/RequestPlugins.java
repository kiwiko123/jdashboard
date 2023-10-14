package com.kiwiko.jdashboard.library.http.client.plugins.v2;

import lombok.Value;

import javax.annotation.Nonnull;
import java.util.List;

@Value
public class RequestPlugins<Plugin extends ApiClientRequestPlugin> {
    @Nonnull List<Plugin> plugins;
}
