package com.kiwiko.jdashboard.webapp.apps.chatroom.web;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomMessageFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomRoomMessage;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomPermissionService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetMessageFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetRoomPermissionsResponse;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageResponse;
import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/chatroom/api")
@JdashboardConfigured
@UserAuthCheck
public class ChatroomAPIController {

    @Inject private ChatroomRoomService chatroomRoomService;
    @Inject private ChatroomPermissionService chatroomPermissionService;

    @GetMapping("/room/{roomId}/messages")
    public ChatroomMessageFeed getMessagesForRoom(
            @PathVariable("roomId") Long roomId,
            @AuthenticatedUser User user) {
        GetMessageFeedParameters getMessageFeedParameters = new GetMessageFeedParameters();
        getMessageFeedParameters.setRoomId(roomId);
        getMessageFeedParameters.setUserId(user.getId());

        // TODO permission check

        return chatroomRoomService.getMessageFeed(getMessageFeedParameters);
    }

    @GetMapping("/room/message/{messageId}")
    public ChatroomRoomMessage getMessageInRoom(
            @PathVariable("messageId") Long messageId) {
        return chatroomRoomService.getMessageForRoom(messageId);
    }

    @GetMapping("/room/{roomId}/permissions")
    public GetRoomPermissionsResponse getRoomPermissions(
            @PathVariable("roomId") Long roomId,
            @AuthenticatedUser User user) {
        GetRoomPermissionsRequest request = new GetRoomPermissionsRequest();
        request.setChatroomMessageRoomId(roomId);
        request.setUserId(user.getId());

        return chatroomPermissionService.getRoomPermissions(request);
    }

    @PostMapping("/room/message")
    public SendMessageResponse sendMessageToRoom(
            @AuthenticatedUser User user,
            @RequestBody SendMessageRequest sendMessageRequest) {
        sendMessageRequest.setSenderUserId(user.getId());
        return chatroomRoomService.sendMessage(sendMessageRequest);
    }
}
