package com.kiwiko.webapp.messages.chatroom.web;

import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.web.MessageDeserializationStrategy;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.api.annotations.CustomRequestBody;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.users.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOriginConfigured
@RestController
public class ChatroomMessageAPIController {

    @Inject
    private ChatroomMessageService messageService;

    @GetMapping("/messages/api/get/thread")
    public ResponseEntity<ResponsePayload> getByUserId(
            @RequestParam("senderUserId") Long senderUserId,
            @RequestParam("recipientUserId") Long recipientUserId) {
        Set<Long> recipientUserIds = Collections.singleton(recipientUserId);
        List<Message> messages = messageService.getBetween(senderUserId, recipientUserIds);

        return new ResponseBuilder()
                .withBody(messages)
                .toResponseEntity();
    }

    @PostMapping("/messages/api/send")
    public ResponseEntity<ResponsePayload> send(
            @CustomRequestBody(strategy = MessageDeserializationStrategy.class) Message message,
            RequestContext requestContext) {
        User currentUser = requestContext.getUser().orElse(null);
        if (currentUser == null) {
            return new ResponseBuilder()
                    .withError("Please log in.")
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .toResponseEntity();
        }

        message.setSenderUserId(currentUser.getId());
        message.setMessageStatus(MessageStatus.SENDING);
        Message result = messageService.send(message);

        return new ResponseBuilder()
                .withBody(result)
                .toResponseEntity();
    }
}
