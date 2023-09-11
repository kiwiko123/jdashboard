package com.kiwiko.jdashboard.timeline.events.service.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.ServiceRequestLock;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventOutput;
import com.kiwiko.jdashboard.timeline.events.service.internal.TimelineEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@JdashboardConfigured
@ServiceRequestLock(clients = "timeline-event-service-client")
@RequestMapping("timeline-api/service-client/timeline-events")
public class TimelineEventServiceClientController {
    @Inject private TimelineEventService timelineEventService;

    @PostMapping("")
    public ResponseEntity<CreateTimelineEventOutput> createTimelineEvent(
            @RequestBody CreateTimelineEventInput createTimelineEventInput) {
        CreateTimelineEventOutput output = timelineEventService.create(createTimelineEventInput);
        return ResponseEntity.ok(output);
    }
}
