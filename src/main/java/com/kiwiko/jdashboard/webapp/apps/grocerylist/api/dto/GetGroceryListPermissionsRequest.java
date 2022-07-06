package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class GetGroceryListPermissionsRequest {
    private Long groceryListId;
    private Long userId;

    public Long getGroceryListId() {
        return groceryListId;
    }

    public void setGroceryListId(Long groceryListId) {
        this.groceryListId = groceryListId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
