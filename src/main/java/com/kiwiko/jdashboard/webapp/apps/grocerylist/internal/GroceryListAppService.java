package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.CreateGroceryListResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryListFeedItem;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.QueryGroceryListsInput;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GroceryListAppService {

    @Inject private GroceryListService groceryListService;

    public GetGroceryListFeedResponse getGroceryListFeed(GetGroceryListFeedRequest request) {
        Objects.requireNonNull(request.getUserId(), "User ID is required to get grocery list feed");

        QueryGroceryListsInput input = new QueryGroceryListsInput();
        input.setUserIds(Collections.singleton(request.getUserId()));
        input.setIsRemoved(false);

        List<GroceryList> groceryLists = groceryListService.query(input);
        List<GroceryListFeedItem> feedItems = new ArrayList<>();

        for (GroceryList groceryList : groceryLists) {
            GroceryListFeedItem feedItem = new GroceryListFeedItem();
            feedItem.setGroceryList(groceryList);
            feedItem.setItemCount(0); // TODO

            feedItems.add(feedItem);
        }

        GetGroceryListFeedResponse response = new GetGroceryListFeedResponse();
        response.setFeedItems(feedItems);
        return response;
    }

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
