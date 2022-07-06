package com.kiwiko.jdashboard.webapp.apps.grocerylist.web;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListPermissionsRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListPermissionsResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.UpdateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.UpdateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListAppService;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListFeedLoader;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListPermissionChecker;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/grocery-list/app-api")
@JdashboardConfigured
@UserAuthCheck
public class GroceryListAppController {

    @Inject private GroceryListAppService groceryListAppService;
    @Inject private GroceryListService groceryListService;
    @Inject private GroceryListFeedLoader groceryListFeedLoader;
    @Inject private GroceryListPermissionChecker groceryListPermissionChecker;

    @GetMapping("/lists/feed")
    public GetGroceryListFeedResponse getGroceryListFeed(@AuthenticatedUser User user) {
        GetGroceryListFeedRequest request = new GetGroceryListFeedRequest();
        request.setUserId(user.getId());

        return groceryListFeedLoader.getGroceryListFeed(request);
    }

    @PostMapping("/lists")
    public CreateGroceryListResponse createGroceryList(
            @RequestBody CreateGroceryListRequest request,
            @AuthenticatedUser User user) {
        request.setUserId(user.getId());
        return groceryListAppService.createGroceryList(request);
    }

    @PatchMapping("/lists/{id}")
    public UpdateGroceryListResponse mergeGroceryList(
            @RequestBody UpdateGroceryListRequest request,
            @PathVariable("id") long groceryListId,
            @AuthenticatedUser User user) {
        request.getGroceryList().setId(groceryListId);
        request.setUserId(user.getId());
        return groceryListAppService.updateGroceryList(request);
    }

    @DeleteMapping("/lists/{id}")
    public GroceryList removeGroceryList(@PathVariable("id") long groceryListId) {
        return groceryListService.delete(groceryListId);
    }

    @GetMapping("/lists/{id}/permissions")
    public GetGroceryListPermissionsResponse getListPermissions(
            @PathVariable("id") Long groceryListId,
            @AuthenticatedUser User user) {
        GetGroceryListPermissionsRequest request = new GetGroceryListPermissionsRequest();
        request.setGroceryListId(groceryListId);
        request.setUserId(user.getId());

        return groceryListPermissionChecker.getListPermissions(request);
    }
}
