package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.data.EnvironmentProperties;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.sessions.PushServiceWebSocketSessionManager;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.PushServiceSubscriberRegistry;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.PushServiceSubscriberRouter;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.SubscribePushServicesCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@ComponentScan(basePackageClasses = PushServiceWebSocketConfiguration.class)
@EnableWebSocket
public class PushServiceWebSocketConfiguration implements WebSocketConfigurer, JdashboardDependencyConfiguration {
    private static final String PUSH_SERVICE_REQUEST_URL = "/push";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(pushServiceTextWebSocketHandler(), PUSH_SERVICE_REQUEST_URL)
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins(EnvironmentProperties.CROSS_ORIGIN_URL);
    }

    @Bean
    public PushServiceTextWebSocketHandler pushServiceTextWebSocketHandler() {
        return new PushServiceTextWebSocketHandler();
    }

    @Bean
    public PushServiceDeserializationHelper pushServiceDeserializationHelper() {
        return new PushServiceDeserializationHelper();
    }

    @Bean
    public PushServiceValidator pushServiceValidator() {
        return new PushServiceValidator();
    }

    @Bean
    public PushServiceWebSocketSessionManager pushServiceWebSocketSessionManager() {
        return new PushServiceWebSocketSessionManager();
    }

    @Bean
    public PushServiceSubscriberRegistry pushServiceSubscriberRegistry() {
        return new PushServiceSubscriberRegistry();
    }

    @Bean
    public PushServiceSubscriberRouter pushServiceSubscriberRouter() {
        return new PushServiceSubscriberRouter();
    }

    @Bean
    public SubscribePushServicesCreator subscribePushServicesCreator() {
        return new SubscribePushServicesCreator();
    }
}
