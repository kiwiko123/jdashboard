package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class GroceryListFeedItem {
    private GroceryList groceryList;
    private int itemCount;

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
}
