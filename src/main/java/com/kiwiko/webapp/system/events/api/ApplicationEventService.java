package com.kiwiko.webapp.system.events.api;

import com.kiwiko.webapp.system.events.api.parameters.ApplicationEventQuery;
import com.kiwiko.webapp.system.events.dto.ApplicationEvent;

import java.util.Optional;
import java.util.Set;

public interface ApplicationEventService {

    Optional<ApplicationEvent> get(long id);

    Set<ApplicationEvent> query(ApplicationEventQuery query);

    ApplicationEvent create(ApplicationEvent event);
}
