package com.kiwiko.webapp.application.events.queue.api.interfaces;

import com.kiwiko.webapp.application.events.queue.api.dto.ApplicationEventQueueItem;

import java.util.List;

public interface ApplicationEventQueue {

    void enqueue(ApplicationEventQueueItem item);

    List<ApplicationEventQueueItem> getActiveItems();
}
