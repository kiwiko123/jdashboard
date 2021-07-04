package com.kiwiko.webapp.notifications.api.queries;

import com.kiwiko.webapp.notifications.data.NotificationStatus;

import java.util.HashSet;
import java.util.Set;

public class GetNotificationsQuery {

    private Long userId;
    private Set<NotificationStatus> statuses = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public GetNotificationsQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Set<NotificationStatus> getStatuses() {
        return statuses;
    }

    public GetNotificationsQuery withStatus(NotificationStatus status) {
        statuses.add(status);
        return this;
    }
}
