package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class CreateGroceryListRequest {
    private Long userId;
    private String listName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
