package com.kiwiko.jdashboard.webapp.permissions.framework.impl;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.webapp.framework.interceptors.internal.SessionRequestHelper;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.Session;
import com.kiwiko.jdashboard.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import com.kiwiko.jdashboard.webapp.permissions.framework.api.annotations.PermissionCheck;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PermissionRequiredInterceptor implements EndpointInterceptor {

    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private PermissionClient permissionClient;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        PermissionCheck permissionCheck = Optional.ofNullable(method.getMethodAnnotation(PermissionCheck.class))
                .orElseGet(() -> method.getMethod().getDeclaringClass().getAnnotation(PermissionCheck.class));
        if (permissionCheck == null) {
            return true;
        }

        Long currentUserId = sessionRequestHelper.getSessionFromRequest(request)
                .map(Session::getUserId)
                .orElse(null);
        if (currentUserId == null) {
            logger.warn(String.format("[%s] Unable to determine current user from request %s", getClass().getName(), request.getRequestURL().toString()));
            return false;
        }

        Set<String> permissionNames = new HashSet<>(Arrays.asList(permissionCheck.value()));
        QueryPermissionsInput queryPermissionsInput = QueryPermissionsInput.newBuilder()
                .setUserIds(Collections.singleton(currentUserId))
                .setPermissionNames(permissionNames)
                .build();

        QueryPermissionsOutput output = permissionClient.query(queryPermissionsInput);
        return output.getPermissions() != null && !output.getPermissions().isEmpty();
    }
}
