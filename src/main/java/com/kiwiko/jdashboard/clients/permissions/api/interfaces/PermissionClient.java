package com.kiwiko.jdashboard.clients.permissions.api.interfaces;

import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.QueryPermissionsOutput;

public interface PermissionClient {

    QueryPermissionsOutput query(QueryPermissionsInput input);

    CreatePermissionOutput create(CreatePermissionInput input);
}
