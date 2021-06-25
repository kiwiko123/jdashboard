package com.kiwiko.webapp.system.events.api.interfaces;

import com.kiwiko.webapp.system.events.api.interfaces.parameters.ApplicationEventQuery;
import com.kiwiko.webapp.system.events.api.dto.ApplicationEvent;

import java.util.Optional;
import java.util.Set;

public interface ApplicationEventService {

    Optional<ApplicationEvent> get(long id);

    Set<ApplicationEvent> query(ApplicationEventQuery query);

    ApplicationEvent create(ApplicationEvent event);
}
