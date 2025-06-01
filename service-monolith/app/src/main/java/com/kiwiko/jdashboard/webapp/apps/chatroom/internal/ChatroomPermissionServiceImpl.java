package com.kiwiko.jdashboard.webapp.apps.chatroom.internal;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomPermissionService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsResponse;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomUserService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ChatroomPermissionServiceImpl implements ChatroomPermissionService {

    @Inject private ChatroomMessageRoomUserService chatroomMessageRoomUserService;

    @Override
    public GetRoomPermissionsResponse getRoomPermissions(GetRoomPermissionsRequest request) {
        Objects.requireNonNull(request, "Request parameters required");
        Objects.requireNonNull(request.getChatroomMessageRoomId(), "Room ID is required");
        Objects.requireNonNull(request.getUserId(), "User ID is required");

        Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers = chatroomMessageRoomUserService.getByRoomIds(Collections.singleton(request.getChatroomMessageRoomId()));
        boolean canAccess = chatroomMessageRoomUsers.stream()
                .map(ChatroomMessageRoomUser::getUserId)
                .anyMatch(userId -> Objects.equals(userId, request.getUserId()));

        GetRoomPermissionsResponse response = new GetRoomPermissionsResponse();
        response.setCanAccess(canAccess);
        return response;
    }
}
