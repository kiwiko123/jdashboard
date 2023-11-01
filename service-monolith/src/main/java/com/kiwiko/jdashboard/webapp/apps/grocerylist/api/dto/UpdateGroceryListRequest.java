package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class UpdateGroceryListRequest {
    private GroceryList groceryList;
    private Long userId;

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
