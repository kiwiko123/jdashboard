package com.kiwiko.jdashboard.services.userauth.web.dto;

import javax.servlet.http.HttpServletResponse;

public class LogUserInInput {
    private UserLoginData userLoginData;
    private HttpServletResponse httpServletResponse;

    public UserLoginData getUserLoginData() {
        return userLoginData;
    }

    public void setUserLoginData(UserLoginData userLoginData) {
        this.userLoginData = userLoginData;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
