package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;

import javax.inject.Inject;
import java.util.Objects;

public class GroceryListAppService {

    @Inject private GroceryListService groceryListService;

    public CreateGroceryListResponse createGroceryList(CreateGroceryListRequest request) {
        Objects.requireNonNull(request.getUserId(), "User ID is required to create a grocery list");
        Objects.requireNonNull(request.getListName(), "List name is required to create a grocery list");

        GroceryList groceryList = new GroceryList();
        groceryList.setUserId(request.getUserId());
        groceryList.setName(request.getListName());

        GroceryList createdGroceryList = groceryListService.create(groceryList);

        CreateGroceryListResponse response = new CreateGroceryListResponse();
        response.setGroceryList(createdGroceryList);
        return response;
    }
}
