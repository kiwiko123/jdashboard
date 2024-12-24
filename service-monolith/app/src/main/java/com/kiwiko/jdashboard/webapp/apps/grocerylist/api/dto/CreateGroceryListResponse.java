package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class CreateGroceryListResponse {
    private GroceryList groceryList;

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }
}
