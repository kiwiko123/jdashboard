package com.kiwiko.webapp.application.events.queue.internal;

import com.kiwiko.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.webapp.application.events.api.interfaces.parameters.ApplicationEventQuery;
import com.kiwiko.webapp.application.events.queue.api.dto.ApplicationEventQueueItem;
import com.kiwiko.webapp.application.events.queue.api.interfaces.ApplicationEventQueue;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ApplicationEventServiceQueue implements ApplicationEventQueue {

    @Inject private ApplicationEventService applicationEventService;

    @Override
    public void enqueue(ApplicationEventQueueItem item) {
        Objects.requireNonNull(item, "Item required");

        ApplicationEvent applicationEvent = item.getApplicationEvent();
        Objects.requireNonNull(applicationEvent, "Application event required");

        String eventType = String.format("%s%s", ApplicationEventQueueConstants.EVENT_TYPE_PREFIX, applicationEvent.getEventType());
        applicationEvent.setEventType(eventType);

        applicationEventService.create(applicationEvent);
    }

    @Override
    public List<ApplicationEventQueueItem> getActiveItems() {
        String eventTypeQuery = ApplicationEventQueueConstants.EVENT_TYPE_PREFIX + '%';
        ApplicationEventQuery query = new ApplicationEventQuery(eventTypeQuery);
        query.setIsRemoved(false);

        return applicationEventService.queryLike(query).stream()
                .sorted(Comparator.comparing(ApplicationEvent::getCreatedDate))
                .map(ApplicationEventQueueItem::fromEvent)
                .collect(Collectors.toList());
    }
}
