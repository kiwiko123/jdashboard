package com.kiwiko.webapp.mvc.security.authentication.internal.events;

public interface UserAuthenticationEventClient {

    void recordLogInEvent(long userId);

    void recordLogOutEvent(long userId);
}
