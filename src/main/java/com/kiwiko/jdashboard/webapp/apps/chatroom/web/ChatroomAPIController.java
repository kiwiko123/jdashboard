package com.kiwiko.jdashboard.webapp.apps.chatroom.web;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomMessageFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomRoomService;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/chatroom/api")
@JdashboardConfigured
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class ChatroomAPIController {

    @Inject private ChatroomRoomService chatroomRoomService;

    @GetMapping("/room/{roomId}/messages")
    public ChatroomMessageFeed getMessagesForRoom(
            @PathVariable("roomId") Long roomId,
            @AuthenticatedUser User user) {
        return null;
    }
}