package com.kiwiko.jdashboard.webapp.clients.permissions.impl;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.webapp.clients.permissions.impl.requests.CorePermissionSet;
import com.kiwiko.jdashboard.webapp.clients.permissions.impl.requests.CreatePermissionApiRequest;
import com.kiwiko.jdashboard.webapp.clients.permissions.impl.requests.QueryPermissionsApiRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.util.Objects;

public class PermissionHttpClient implements PermissionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private Logger logger;

    @Override
    public QueryPermissionsOutput query(QueryPermissionsInput input) {
        Objects.requireNonNull(input, "Input is required");

        QueryPermissionsApiRequest apiRequest = new QueryPermissionsApiRequest(input);
        ApiResponse<CorePermissionSet> apiResponse = null;

        try {
            apiResponse = jdashboardApiClient.synchronousCall(apiRequest);
        } catch (Exception e) {
            logger.error(String.format("Error querying for permissions %s", input), e);
        }

        QueryPermissionsOutput output = new QueryPermissionsOutput();
        if (apiResponse != null) {
            output.setPermissions(apiResponse.getPayload());
        }

        return output;
    }

    @Override
    public CreatePermissionOutput create(CreatePermissionInput input) {
        CreatePermissionApiRequest apiRequest = new CreatePermissionApiRequest(input);
        ApiResponse<CreatePermissionOutput> apiResponse = null;

        try {
            apiResponse = jdashboardApiClient.synchronousCall(apiRequest);
        } catch (Exception e) {
            // TODO log
        }

        return apiResponse.getPayload();
    }
}
