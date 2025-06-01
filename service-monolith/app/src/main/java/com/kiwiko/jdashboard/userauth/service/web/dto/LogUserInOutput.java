package com.kiwiko.jdashboard.userauth.service.web.dto;

import javax.annotation.Nullable;

public class LogUserInOutput {
    private @Nullable Long userId;

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }
}
