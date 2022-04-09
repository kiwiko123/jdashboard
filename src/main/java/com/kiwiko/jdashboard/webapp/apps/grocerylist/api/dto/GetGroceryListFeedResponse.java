package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

import java.util.List;

public class GetGroceryListFeedResponse {
    private List<GroceryListFeedItem> feedItems;

    public List<GroceryListFeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<GroceryListFeedItem> feedItems) {
        this.feedItems = feedItems;
    }
}
