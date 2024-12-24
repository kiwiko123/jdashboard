package com.kiwiko.jdashboard.framework.permissions.internal;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.webapp.framework.interceptors.internal.SessionRequestHelper;
import com.kiwiko.jdashboard.sessions.client.api.dto.Session;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserPermissionCheckInterceptor implements RequestInterceptor {

    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private PermissionClient permissionClient;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        UserPermissionCheck userPermissionCheck = Optional.ofNullable(method.getMethodAnnotation(UserPermissionCheck.class))
                .orElseGet(() -> method.getMethod().getDeclaringClass().getAnnotation(UserPermissionCheck.class));
        if (userPermissionCheck == null) {
            return true;
        }

        Long currentUserId = sessionRequestHelper.getSessionFromRequest(request)
                .map(Session::getUserId)
                .orElse(null);
        if (currentUserId == null) {
            logger.warn(String.format("[%s] Unable to determine current user from request %s", getClass().getName(), request.getRequestURL().toString()));
            return false;
        }

        Set<String> permissionNames = new HashSet<>(Arrays.asList(userPermissionCheck.value()));
        QueryPermissionsInput queryPermissionsInput = QueryPermissionsInput.newBuilder()
                .setUserIds(Collections.singleton(currentUserId))
                .setPermissionNames(permissionNames)
                .build();

        QueryPermissionsOutput output = permissionClient.query(queryPermissionsInput);
        boolean hasPermissions = output.getPermissions() != null && !output.getPermissions().isEmpty();

        if (!hasPermissions) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Permission denied");
        }

        return hasPermissions;
    }
}
