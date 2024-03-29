package com.kiwiko.jdashboard.webapp.notifications.web;

import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.jdashboard.webapp.notifications.api.NotificationService;
import com.kiwiko.jdashboard.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.jdashboard.webapp.notifications.data.Notification;
import com.kiwiko.jdashboard.webapp.notifications.data.NotificationStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Queue;

@CrossOriginConfigured
@UserAuthCheck
@RestController
public class NotificationsAPIController {

    @Inject private NotificationService notificationService;

    @GetMapping("/notifications/api/{notificationId}")
    public ResponsePayload get(@PathVariable("notificationId") Long notificationId) {
        Notification notification = notificationService.get(notificationId).orElse(null);
        return new ResponseBuilder()
                .withBody(notification)
                .build();
    }

    @PostMapping("/notifications/api")
    public ResponsePayload create(@RequestBody Notification notification) {
        Notification result = notificationService.create(notification);
        return new ResponseBuilder()
                .withBody(result)
                .build();
    }

    @PutMapping("/notifications/api/{notificationId}")
    public ResponsePayload update(
            @PathVariable("notificationId") Long notificationId,
            @RequestBody Notification notification) {
        notification.setId(notificationId);;
        Notification result = notificationService.update(notification);
        return new ResponseBuilder()
                .withBody(result)
                .build();
    }

    @DeleteMapping("/notifications/api/{notificationId}")
    public ResponsePayload delete(@PathVariable("notificationId") Long notificationId) {
        notificationService.delete(notificationId);
        return ResponseBuilder.ok();
    }

    @GetMapping("/notifications/api/users/{userId}/pending")
    public ResponsePayload getPendingForUser(@PathVariable("userId") Long userId) {
        GetNotificationsQuery query = new GetNotificationsQuery()
                .setUserId(userId)
                .withStatus(NotificationStatus.SENT);
        Queue<Notification> pendingNotifications = notificationService.query(query);
        return new ResponseBuilder()
                .withBody(pendingNotifications)
                .build();
    }
}
