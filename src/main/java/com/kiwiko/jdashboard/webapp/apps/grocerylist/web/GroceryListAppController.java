package com.kiwiko.jdashboard.webapp.apps.grocerylist.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListAppService;
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

    @PostMapping("/lists")
    public CreateGroceryListResponse createGroceryList(@RequestBody CreateGroceryListRequest request) {
        return groceryListAppService.createGroceryList(request);
    }
}
