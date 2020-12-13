package com.kiwiko.webapp.chatroom.web;

import com.kiwiko.webapp.chatroom.web.helpers.ChatroomResponseHelper;
import com.kiwiko.webapp.chatroom.web.helpers.data.MessageDTO;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.WebResponse;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;

@AuthenticationRequired
@CrossOriginConfigured
@Controller
public class ChatroomAPIController {

    @Inject
    private ChatroomResponseHelper chatroomResponseHelper;

    @GetMapping("/chatroom/api/message/{messageId}")
    public WebResponse get(@PathVariable("messageId") Long messageId) {
        MessageDTO message = chatroomResponseHelper.get(messageId).orElse(null);
        return new ResponseBuilder()
                .withBody(message)
                .build();
    }
}
