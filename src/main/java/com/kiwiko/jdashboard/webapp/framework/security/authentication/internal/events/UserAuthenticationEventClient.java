package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events;

public interface UserAuthenticationEventClient {

    void recordLogInEvent(long userId);

    void recordLogOutEvent(long userId);
}
