package com.kiwiko.jdashboard.permissions.client.impl;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.permissions.client.impl.requests.CorePermissionSet;
import com.kiwiko.jdashboard.permissions.client.impl.requests.CreatePermissionApiRequest;
import com.kiwiko.jdashboard.permissions.client.impl.requests.QueryPermissionsApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
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
        ClientResponse<CorePermissionSet> response = null;

        try {
            response = jdashboardApiClient.synchronousCall(apiRequest);
        } catch (Exception e) {
            logger.error(String.format("Error querying for permissions %s", input), e);
        }

        QueryPermissionsOutput output = new QueryPermissionsOutput();
        output.setPermissions(response.getPayload());

        return output;
    }

    @Override
    public CreatePermissionOutput create(CreatePermissionInput input) {
        CreatePermissionApiRequest apiRequest = new CreatePermissionApiRequest(input);
        ClientResponse<CreatePermissionOutput> response = null;

        try {
            response = jdashboardApiClient.synchronousCall(apiRequest);
        } catch (Exception e) {
            // TODO log
        }

        return response.getPayload();
    }
}
