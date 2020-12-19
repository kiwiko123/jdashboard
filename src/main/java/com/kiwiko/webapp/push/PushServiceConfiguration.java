package com.kiwiko.webapp.push;

import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookConfigurationCreator;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.push.api.PushServiceConfigurationCreator;
import com.kiwiko.webapp.push.api.PushServiceRegistry;
import com.kiwiko.webapp.push.internal.impl.PushNotificationDeliveryService;
import com.kiwiko.webapp.push.internal.PushRequestHelper;
import com.kiwiko.webapp.push.internal.PushServiceSessionManager;
import com.kiwiko.webapp.push.internal.PushServiceShutdownHook;
import com.kiwiko.webapp.push.internal.PushWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.inject.Inject;

@Configuration
@EnableWebSocket
public class PushServiceConfiguration implements WebSocketConfigurer {

    @Inject private LifeCycleHookConfigurationCreator lifeCycleHookConfigurationCreator;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(pushWebSocketHandler(), "/push")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins(EnvironmentProperties.CROSS_ORIGIN_URL);
    }

    @Bean
    public WebSocketHandler pushWebSocketHandler() {
        return new PushWebSocketHandler();
    }

    @Bean
    public PushServiceRegistry pushServiceRegistry() {
        return new PushServiceRegistry();
    }

    @Bean
    public PushServiceConfigurationCreator pushServiceConfigurationCreator() {
        return new PushServiceConfigurationCreator();
    }

    @Bean
    public PushServiceSessionManager pushServiceSessionManager() {
        return new PushServiceSessionManager();
    }

    @Bean
    public PushRequestHelper pushRequestHelper() {
        return new PushRequestHelper();
    }

    @Bean
    public PushServiceShutdownHook pushServiceShutdownHook() {
        return lifeCycleHookConfigurationCreator.createShutdownHook(PushServiceShutdownHook::new);
    }
}
