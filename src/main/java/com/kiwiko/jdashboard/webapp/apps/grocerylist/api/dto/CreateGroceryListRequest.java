package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class CreateGroceryListRequest {
    private Field listName;

    public Field getListName() {
        return listName;
    }

    public void setListName(Field listName) {
        this.listName = listName;
    }

    public static class Field {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
