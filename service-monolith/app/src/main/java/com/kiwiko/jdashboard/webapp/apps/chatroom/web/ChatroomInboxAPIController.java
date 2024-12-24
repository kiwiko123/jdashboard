package com.kiwiko.jdashboard.webapp.apps.chatroom.web;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.NewChatroom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.CreateChatroomFormInput;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;
import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/chatroom/api/inbox")
@JdashboardConfigured
@UserAuthCheck
public class ChatroomInboxAPIController {

    @Inject private ChatroomInboxService chatroomInboxService;

    @GetMapping("/feed")
    public ChatroomInboxFeed getFeed(@AuthenticatedUser User currentUser) {
        GetInboxFeedParameters parameters = new GetInboxFeedParameters();
        parameters.setUserId(currentUser.getId());

        return chatroomInboxService.getInboxFeed(parameters);
    }

    @PostMapping("/form/room")
    public NewChatroom createNewRoom(
            @AuthenticatedUser User currentUser,
            @RequestBody CreateChatroomFormInput input) {
        input.setUserId(currentUser.getId());
        return chatroomInboxService.createRoomFromForm(input);
    }
}
