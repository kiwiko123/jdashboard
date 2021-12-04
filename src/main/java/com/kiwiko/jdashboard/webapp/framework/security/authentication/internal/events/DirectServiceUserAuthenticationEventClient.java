package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events;

import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;

import javax.inject.Inject;

public class DirectServiceUserAuthenticationEventClient implements UserAuthenticationEventClient {

    @Inject private ApplicationEventService applicationEventService;

    @Override
    public void recordLogInEvent(long userId) {
        ApplicationEvent event = new ApplicationEvent.Builder(UserAuthenticationEventConstants.USER_LOG_IN_EVENT_KEY)
                .setEventKey(Long.toString(userId))
                .build();

        applicationEventService.create(event);
    }

    @Override
    public void recordLogOutEvent(long userId) {
        ApplicationEvent event = new ApplicationEvent.Builder(UserAuthenticationEventConstants.USER_LOG_OUT_EVENT_KEY)
                .setEventKey(Long.toString(userId))
                .build();

        applicationEventService.create(event);
    }
}
