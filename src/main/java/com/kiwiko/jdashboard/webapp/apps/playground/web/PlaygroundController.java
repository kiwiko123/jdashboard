package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.framework.jobscheduler.api.JobFunctionOutput;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobScheduler;
import com.kiwiko.jdashboard.framework.jobscheduler.api.ScheduleJobInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEventClient;
import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.users.client.api.interfaces.UserClient;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.ServiceRequestLock;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

@CrossOriginConfigured
@Controller
public class PlaygroundController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaygroundController.class);

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserClient userClient;
    @Inject private PermissionClient permissionClient;
    @Inject private PushService pushService;
    @Inject private TimelineEventClient timelineEventClient;
    @Inject private JobScheduler jobScheduler;

    @GetMapping("/playground-api/test")
    @ResponseBody
    public boolean test(@AuthenticatedUser User currentUser) throws Exception {
        for (int i = 0; i < 100; ++i) {
            final int iteration = i;
            ScheduleJobInput scheduleJobInput = ScheduleJobInput.builder()
                    .jobName(String.format("Playground test job %d", i))
                    .scheduledRunTime(Instant.now().plus(Duration.ofSeconds(20)))
                    .function((input) -> {
                        LOGGER.info("Playground test job!");

                        CreateTimelineEventInput createTimelineEventInput = CreateTimelineEventInput.builder()
                                .eventName("TEST_playground-job-test")
                                .eventKey(Integer.toString(iteration))
                                .currentUserId(currentUser.getId())
                                .build();
                        timelineEventClient.pushNewTimelineEvent(createTimelineEventInput);

                        Thread.sleep(Duration.ofSeconds(5).toMillis());
                        return JobFunctionOutput.create();
                    })
                    .build();

            jobScheduler.queue(scheduleJobInput);
        }

        return true;
    }

    @ServiceRequestLock
    @PostMapping("/playground-api/test")
    @ResponseBody
    public String testPost(@RequestBody User user) throws Exception {
        return "Success! " + user.toString();
    }
}
