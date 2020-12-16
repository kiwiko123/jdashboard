package com.kiwiko.webapp.push.api;

import javax.inject.Inject;
import java.util.function.Supplier;

public class PushServiceConfigurationCreator {

    @Inject private PushServiceRegistry pushServiceRegistry;

    /**
     * Creates an instance of {@link P}, to be used as a {@link org.springframework.context.annotation.Bean}
     * in a {@link org.springframework.context.annotation.Configuration}.
     *
     * Rather than making a {@link org.springframework.context.annotation.Bean} that returns
     * a {@code new MyPushServiceImplementation()}, use this to ensure that the service is properly set-up.
     *
     * @param createPushService a function that returns a new instance of {@link P}
     * @param <P> a class that implements {@link PushService}
     * @return the created push service {@link org.springframework.context.annotation.Bean}
     */
    public <P extends PushService> P create(Supplier<P> createPushService) {
        P service = createPushService.get();
        pushServiceRegistry.register(service);
        return service;
    }
}
