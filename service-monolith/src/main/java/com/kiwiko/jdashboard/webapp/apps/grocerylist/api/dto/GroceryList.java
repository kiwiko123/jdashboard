package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;

public class GroceryList extends SoftDeletableDataEntityDTO {
    private Long userId;
    private @Nullable String name;
    private Instant createdDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
