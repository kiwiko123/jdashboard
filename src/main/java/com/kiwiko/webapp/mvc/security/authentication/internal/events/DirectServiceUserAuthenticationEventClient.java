package com.kiwiko.webapp.mvc.security.authentication.internal.events;

import com.kiwiko.webapp.system.events.api.ApplicationEventService;
import com.kiwiko.webapp.system.events.dto.ApplicationEvent;

import javax.inject.Inject;

public class DirectServiceUserAuthenticationEventClient implements UserAuthenticationEventClient {

    @Inject private ApplicationEventService applicationEventService;

    @Override
    public void recordLogInEvent(long userId) {
        ApplicationEvent event = new ApplicationEvent.Builder("user_log_in")
                .setEventKey(Long.toString(userId))
                .build();

        applicationEventService.create(event);
    }

    @Override
    public void recordLogOutEvent(long userId) {
        ApplicationEvent event = new ApplicationEvent.Builder("user_log_out")
                .setEventKey(Long.toString(userId))
                .build();

        applicationEventService.create(event);
    }
}
