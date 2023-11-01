package com.kiwiko.jdashboard.permissions.client.api.interfaces;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsOutput;

public interface PermissionClient {

    QueryPermissionsOutput query(QueryPermissionsInput input);

    CreatePermissionOutput create(CreatePermissionInput input);
}
