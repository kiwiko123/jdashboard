package com.kiwiko.jdashboard.timeline.events.service.internal;

import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventOutput;
import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEvent;
import com.kiwiko.jdashboard.timeline.events.service.internal.data.TimelineEventEntityDataAccessObject;
import com.kiwiko.jdashboard.timeline.events.service.internal.data.TimelineEventEntityMapper;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

public class TimelineEventService {
    @Inject private TimelineEventEntityDataAccessObject timelineEventEntityDataAccessObject;
    @Inject private TimelineEventEntityMapper timelineEventEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor createReadUpdateDeleteExecutor;

    public Optional<TimelineEvent> get(long id) {
        return createReadUpdateDeleteExecutor.get(id, timelineEventEntityDataAccessObject, timelineEventEntityMapper);
    }

    public TimelineEvent create(TimelineEvent obj) {
        return createReadUpdateDeleteExecutor.create(obj, timelineEventEntityDataAccessObject, timelineEventEntityMapper);
    }

    public CreateTimelineEventOutput create(CreateTimelineEventInput input) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setEventName(input.getEventName());
        timelineEvent.setEventKey(input.getEventKey());
        timelineEvent.setMetadata(input.getMetadata());
        timelineEvent.setCreatedDate(Instant.now());
        timelineEvent.setCreatedByUserId(input.getCurrentUserId());

        TimelineEvent createdEvent = create(timelineEvent);
        return new CreateTimelineEventOutput(createdEvent);
    }

    public TimelineEvent update(TimelineEvent obj) {
        return createReadUpdateDeleteExecutor.update(obj, timelineEventEntityDataAccessObject, timelineEventEntityMapper);
    }

    public TimelineEvent merge(TimelineEvent obj) {
        return createReadUpdateDeleteExecutor.merge(obj, timelineEventEntityDataAccessObject, timelineEventEntityMapper);
    }
}
