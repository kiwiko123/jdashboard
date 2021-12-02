package com.kiwiko.jdashboard.webapp.apps.chatroom.web;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxItem;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/chatroom/api/inbox")
@JdashboardConfigured
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class ChatroomInboxAPIController {

    @Inject private ChatroomInboxService chatroomInboxService;

    @GetMapping("/feed")
    public ChatroomInboxFeed getFeed(@AuthenticatedUser User currentUser) {
        GetInboxFeedParameters parameters = new GetInboxFeedParameters();
        parameters.setUserId(currentUser.getId());

        return chatroomInboxService.getInboxFeed(parameters);
    }

    @PostMapping("/room")
    public ChatroomInboxItem createNewRoom(@AuthenticatedUser User currentUser) {
        return null;
    }
}
