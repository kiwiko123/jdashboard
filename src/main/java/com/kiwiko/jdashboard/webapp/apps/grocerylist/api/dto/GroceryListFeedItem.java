package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

import java.time.Instant;

public class GroceryListFeedItem {
    private GroceryList groceryList;
    private int itemCount;
    private Instant lastUpdatedDate;

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
