package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsResponse;

public interface ChatroomPermissionService {

    GetRoomPermissionsResponse getRoomPermissions(GetRoomPermissionsRequest request);
}
