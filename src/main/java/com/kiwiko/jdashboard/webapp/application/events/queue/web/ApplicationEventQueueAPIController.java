package com.kiwiko.jdashboard.webapp.application.events.queue.web;

import com.kiwiko.jdashboard.webapp.application.events.queue.api.dto.ApplicationEventQueueItem;
import com.kiwiko.jdashboard.webapp.application.events.queue.api.interfaces.ApplicationEventQueue;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserAuthCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
@JdashboardConfigured
@UserAuthCheck
@RequestMapping("/application-event-queue/api")
public class ApplicationEventQueueAPIController {

    @Inject private ApplicationEventQueue applicationEventQueue;

    @GetMapping("/active-events")
    @ResponseBody
    public List<ApplicationEventQueueItem> getActiveEvents() {
        return applicationEventQueue.getActiveItems();
    }
}
