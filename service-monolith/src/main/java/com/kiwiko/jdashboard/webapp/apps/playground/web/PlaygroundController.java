package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobFunctionOutput;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobScheduler;
import com.kiwiko.jdashboard.framework.jobscheduler.api.ScheduleJobInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEventClient;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.DefaultJdashboardApiClientPlugins;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

@CrossOriginConfigured
@Controller
public class PlaygroundController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaygroundController.class);

    @Inject private FeatureFlagClient featureFlagClient;
    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserClient userClient;
    @Inject private PermissionClient permissionClient;
    @Inject private PushService pushService;
    @Inject private TimelineEventClient timelineEventClient;
    @Inject private JobScheduler jobScheduler;

    @GetMapping("/playground-api/ff/on")
    @ResponseBody
    public ClientResponse<ResolvedFeatureFlag> ffOn(@RequestParam("f") String featureFlagName) {
        return featureFlagClient.turnOn(featureFlagName);
    }

    @GetMapping("/playground-api/ff/off")
    @ResponseBody
    public ClientResponse<ResolvedFeatureFlag> ffOff(@RequestParam("f") String featureFlagName) {
        return featureFlagClient.turnOff(featureFlagName);
    }

    @GetMapping("/playground-api/test")
    @ResponseBody
    public ClientResponse<String> testPostFromGet() throws Exception {
        TestPostApiRequestV2 request = new TestPostApiRequestV2();

        TestPostApiRequestV2Context requestContext = new TestPostApiRequestV2Context(request);

        return jdashboardApiClient.synchronousCall(request, requestContext);
    }

    @ServiceRequestLock
    @PostMapping("/playground-api/test")
    @ResponseBody
    public String testPost(@RequestBody String message) throws Exception {
        return String.format("The message is: \"%s\"", message);
    }
}
