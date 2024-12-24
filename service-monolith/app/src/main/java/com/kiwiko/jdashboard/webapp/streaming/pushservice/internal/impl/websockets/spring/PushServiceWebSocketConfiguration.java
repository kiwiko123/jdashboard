package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.di.DependencyInjectionUtilConfiguration;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.notifications.internal.push.NotificationPushServiceSubscriberConfiguration;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.PushServiceConfiguration;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.sessions.PushServiceWebSocketSessionManager;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.PushServiceSubscriberRegistry;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.PushServiceSubscriberRouter;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.SubscribePushServicesCreator;
import org.springframework.beans.factory.annotation.Value;
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
public class PushServiceWebSocketConfiguration implements WebSocketConfigurer {
    private static final String PUSH_SERVICE_REQUEST_URL = "/push";

    @Value("${jdashboard.framework.security.csrf.allowed-cross-origin-urls}")
    private String[] allowedCrossOriginUrls;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(pushServiceTextWebSocketHandler(), PUSH_SERVICE_REQUEST_URL)
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins(allowedCrossOriginUrls);
    }

    @Bean
    @ConfiguredBy({GsonJsonConfiguration.class, PushServiceConfiguration.class, LoggingConfiguration.class})
    public PushServiceTextWebSocketHandler pushServiceTextWebSocketHandler() {
        return new PushServiceTextWebSocketHandler();
    }

    @Bean
    @ConfiguredBy({GsonJsonConfiguration.class, LoggingConfiguration.class})
    public PushServiceDeserializationHelper pushServiceDeserializationHelper() {
        return new PushServiceDeserializationHelper();
    }

    @Bean
    public PushServiceValidator pushServiceValidator() {
        return new PushServiceValidator();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public PushServiceWebSocketSessionManager pushServiceWebSocketSessionManager() {
        return new PushServiceWebSocketSessionManager();
    }

    @Bean
    public PushServiceSubscriberRegistry pushServiceSubscriberRegistry() {
        return new PushServiceSubscriberRegistry();
    }

    @Bean
    @ConfiguredBy({DependencyInjectionUtilConfiguration.class, LoggingConfiguration.class})
    public PushServiceSubscriberRouter pushServiceSubscriberRouter() {
        return new PushServiceSubscriberRouter();
    }

    @Bean
    @ConfiguredBy({LoggingConfiguration.class, NotificationPushServiceSubscriberConfiguration.class})
    public SubscribePushServicesCreator subscribePushServicesCreator() {
        return new SubscribePushServicesCreator();
    }
}
