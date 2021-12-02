package com.kiwiko.jdashboard.webapp.featureflags.web;

import com.kiwiko.jdashboard.webapp.clients.users.api.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUserQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagEventClient;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagResolver;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.webapp.featureflags.web.responses.FeatureFlagListItem;
import com.kiwiko.jdashboard.webapp.mvc.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.mvc.json.deserialization.api.impl.GsonRequestBodyDeserializationStrategy;
import com.kiwiko.jdashboard.webapp.mvc.json.deserialization.api.interfaces.CustomRequestBody;
import com.kiwiko.jdashboard.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Controller
@JdashboardConfigured
public class FeatureFlagAPIController {

    @Inject private FeatureFlagAPIHelper featureFlagAPIHelper;
    @Inject private FeatureFlagEventClient featureFlagEventClient;
    @Inject private FeatureFlagService featureFlagService;
    @Inject private FeatureFlagResolver featureFlagResolver;
    @Inject private UserClient userClient;

    @GetMapping("/feature-flags/api/test")
    public ResponsePayload test() throws InterruptedException {
        GetUserQuery queryOne = new GetUserQuery();
        queryOne.setId(1L);

        GetUserQuery queryTwo = new GetUserQuery();
        queryTwo.setId(2L);

        GetUsersQuery query = new GetUsersQuery();
        query.setQueries(Arrays.asList(queryOne, queryTwo));

        List<User> users = userClient.getByQuery(query);
        return ResponseBuilder.payload(users);
    }

    @GetMapping("/feature-flags/api/resolve")
    public ResponsePayload resolveFlag(
            @RequestParam("name") String name,
            @RequestParam(value = "userId", required = false) @Nullable Long userId) {
        boolean isResolved = featureFlagResolver.resolve(name, userId);
        return ResponseBuilder.payload(isResolved);
    }

    @AuthenticationRequired
    @GetMapping("/feature-flags/api/list")
    public ResponsePayload getFeatureFlagList() {
        List<FeatureFlagListItem> listItems = featureFlagAPIHelper.getListItems();
        return ResponseBuilder.payload(listItems);
    }

    @AuthenticationRequired
    @GetMapping("/feature-flags/api/{id}")
    public ResponsePayload getById(@PathVariable("id") long id) {
        FeatureFlag flag = featureFlagService.get(id).orElse(null);
        return ResponseBuilder.payload(flag);
    }

    @AuthenticationRequired
    @PostMapping("/feature-flags/api")
    @ResponseBody
    public FeatureFlag create(@RequestBody FeatureFlag featureFlag) {
        featureFlagEventClient.createCreateFeatureFlagEvent(featureFlag);
        return featureFlagService.create(featureFlag);
    }

    @AuthenticationRequired
    @PutMapping("/feature-flags/api/{id}")
    @ResponseBody
    public FeatureFlag update(
            @PathVariable("id") long id,
            @CustomRequestBody(strategy = GsonRequestBodyDeserializationStrategy.class) FeatureFlag featureFlag) {
        featureFlag.setId(id);
        featureFlagEventClient.createUpdateFeatureFlagEvent(featureFlag);
        return featureFlagService.update(featureFlag);
    }

    @AuthenticationRequired
    @PatchMapping("/feature-flags/api/{id}")
    @ResponseBody
    public FeatureFlag merge(
            @PathVariable("id") long id,
            @RequestBody FeatureFlag featureFlag) {
        featureFlag.setId(id);
        featureFlagEventClient.createMergeFeatureFlagEvent(featureFlag);
        return featureFlagService.merge(featureFlag);
    }

    @AuthenticationRequired
    @DeleteMapping("/feature-flags/api/{id}")
    public ResponsePayload remove(@PathVariable("id") long id) {
        featureFlagEventClient.createDeleteFeatureFlagEvent(id);

        featureFlagService.delete(id);
        return ResponseBuilder.ok();
    }
}
