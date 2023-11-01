package com.kiwiko.jdashboard.timeline.events.client.api;

public interface TimelineEventClient {

    void pushNewTimelineEvent(CreateTimelineEventInput input);
}
