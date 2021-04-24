package com.kiwiko.webapp.featureFlags.web;

import com.kiwiko.webapp.clients.users.api.UserClient;
import com.kiwiko.webapp.clients.users.api.parameters.GetUserQuery;
import com.kiwiko.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.webapp.featureFlags.api.FeatureFlagResolver;
import com.kiwiko.webapp.featureFlags.api.FeatureFlagService;
import com.kiwiko.webapp.featureFlags.dto.FeatureFlag;
import com.kiwiko.webapp.featureFlags.web.responses.FeatureFlagListItem;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Controller
@CrossOriginConfigured
public class FeatureFlagAPIController {

    @Inject private FeatureFlagAPIHelper featureFlagAPIHelper;
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
    public ResponsePayload create(@RequestBody FeatureFlag featureFlag) {
        FeatureFlag createdFlag = featureFlagService.create(featureFlag);
        return ResponseBuilder.payload(createdFlag);
    }

    @AuthenticationRequired
    @PutMapping("/feature-flags/api/{id}")
    public ResponsePayload update(
            @PathVariable("id") long id,
            @RequestBody FeatureFlag featureFlag) {
        featureFlag.setId(id);
        FeatureFlag updatedFlag = featureFlagService.update(featureFlag);
        return ResponseBuilder.payload(updatedFlag);
    }

    @AuthenticationRequired
    @DeleteMapping("/feature-flags/api/{id}")
    public ResponsePayload remove(@PathVariable("id") long id) {
        featureFlagService.delete(id);
        return ResponseBuilder.ok();
    }
}
