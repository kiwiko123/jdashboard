package com.kiwiko.jdashboard.webapp.application.events.queue.api.dto;

import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;

public class ApplicationEventQueueItem {

    public static ApplicationEventQueueItem fromEvent(ApplicationEvent event) {
        ApplicationEventQueueItem item = new ApplicationEventQueueItem();
        item.setApplicationEvent(event);
        return item;
    }

    private ApplicationEvent applicationEvent;

    public ApplicationEvent getApplicationEvent() {
        return applicationEvent;
    }

    public void setApplicationEvent(ApplicationEvent applicationEvent) {
        this.applicationEvent = applicationEvent;
    }
}
