package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.UpdateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.UpdateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.exceptions.GroceryListRuntimeException;

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

    public UpdateGroceryListResponse updateGroceryList(UpdateGroceryListRequest request) {
        Objects.requireNonNull(request.getGroceryList(), "Grocery list is required to update");
        Objects.requireNonNull(request.getGroceryList().getId(), "ID is required");

        GroceryList existingGroceryList = groceryListService.get(request.getGroceryList().getId())
                .orElseThrow(() -> new GroceryListRuntimeException("No existing record found"));
        if (!Objects.equals(request.getUserId(), existingGroceryList.getUserId())) {
            throw new GroceryListRuntimeException("Users can only update their own grocery lists");
        }

        GroceryList updatedGroceryList = groceryListService.merge(request.getGroceryList());

        UpdateGroceryListResponse response = new UpdateGroceryListResponse();
        response.setGroceryList(updatedGroceryList);
        return response;
    }
}
