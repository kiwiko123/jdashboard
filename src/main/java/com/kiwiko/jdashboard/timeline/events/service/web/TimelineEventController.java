package com.kiwiko.jdashboard.timeline.events.service.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEvent;
import com.kiwiko.jdashboard.timeline.events.service.internal.TimelineEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@UserPermissionCheck(PermissionNames.ADMIN)
@RequestMapping("timeline-api/public/timeline-events")
public class TimelineEventController {
    @Inject private TimelineEventService timelineEventService;

    @GetMapping("/{id}")
    public TimelineEvent getById(@PathVariable("id") long id) {
        return timelineEventService.get(id).orElse(null);
    }

    @PostMapping("")
    public TimelineEvent create(@RequestBody TimelineEvent timelineEvent) {
        return timelineEventService.create(timelineEvent);
    }

    @PutMapping("/{id}")
    public TimelineEvent update(@PathVariable("id") long id, @RequestBody TimelineEvent timelineEvent) {
        timelineEvent.setId(id);
        return timelineEventService.update(timelineEvent);
    }

    @PatchMapping("/{id}")
    public TimelineEvent merge(@PathVariable("id") long id, @RequestBody TimelineEvent timelineEvent) {
        timelineEvent.setId(id);
        return timelineEventService.merge(timelineEvent);
    }
}
