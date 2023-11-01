package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListPermissionsRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListPermissionsResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.QueryGroceryListsInput;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GroceryListPermissionChecker {

    @Inject private GroceryListService groceryListService;

    public GetGroceryListPermissionsResponse getListPermissions(GetGroceryListPermissionsRequest request) {
        Objects.requireNonNull(request.getGroceryListId(), "Grocery list ID is required");
        Objects.requireNonNull(request.getUserId(), "User ID is required");

        QueryGroceryListsInput queryGroceryListsInput = new QueryGroceryListsInput();
        queryGroceryListsInput.setIds(Collections.singleton(request.getGroceryListId()));
        queryGroceryListsInput.setUserIds(Collections.singleton(request.getUserId()));

        List<GroceryList> groceryLists = groceryListService.query(queryGroceryListsInput);

        GetGroceryListPermissionsResponse response = new GetGroceryListPermissionsResponse();
        response.setCanAccess(!groceryLists.isEmpty());

        return response;
    }
}
