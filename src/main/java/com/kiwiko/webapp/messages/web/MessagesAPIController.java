package com.kiwiko.webapp.messages.web;

import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.web.helpers.MessagesResponseHelper;
import com.kiwiko.webapp.messages.web.helpers.data.MessageDTO;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.api.annotations.CustomRequestBody;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.json.data.WebResponse;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.users.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.Instant;
import java.util.*;

@AuthenticationRequired
@CrossOriginConfigured
@Controller
public class MessagesAPIController {

    // TODO make registry by MessageType
    @Inject
    private ChatroomMessageService messageService;

    @Inject private MessagesResponseHelper messagesResponseHelper;

    @GetMapping("/messages/api/get/thread")
    public WebResponse getByUserId(
            @RequestParam("senderUserId") Long senderUserId,
            @RequestParam("recipientUserId") Long recipientUserId,
            @RequestParam(value = "minimumSentDate", required = false) @Nullable Instant minimumSentDate) {
        Set<Long> recipientUserIds = Collections.singleton(recipientUserId);
        GetBetweenParameters parameters = new GetBetweenParameters()
                .withSenderUserId(senderUserId)
                .withRecipientUserIds(recipientUserIds)
                .withMinimumSentDate(minimumSentDate);
        List<MessageDTO> messages = messagesResponseHelper.getMessagesInThread(parameters);

        return new ResponseBuilder()
                .withBody(messages)
                .build();
    }

    @GetMapping("/messages/api/get/users/{userId}/previews")
    public WebResponse getPreviewsByUserId(
            @PathVariable("userId") Long userId) {
        List<MessagePreview> previews = messageService.getMessagePreviewsForUser(userId);

        return new ResponseBuilder()
                .withBody(previews)
                .build();
    }

    @PostMapping("/messages/api/send")
    public ResponseEntity<ResponsePayload> send(
            @CustomRequestBody(strategy = MessageDeserializationStrategy.class) Message message,
            RequestContext requestContext) throws ClientUnreachablePushException {
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

    @PutMapping("/messages/api/{messageId}/confirm")
    public ResponseEntity<ResponsePayload> confirmMessageDelivery(
            @PathVariable("messageId") Long messageId,
            RequestContext requestContext) {
        User currentUser = requestContext.getUser().orElse(null);
        if (currentUser == null) {
            return new ResponseBuilder()
                    .withError("Please log in.")
                    .withStatus(HttpStatus.UNAUTHORIZED)
                    .toResponseEntity();
        }

        Message message = messageService.get(messageId)
                .orElse(null);
        if (message == null) {
            return new ResponseBuilder()
                    .withError("Failed to find message.")
                    .withStatus(HttpStatus.PRECONDITION_FAILED)
                    .toResponseEntity();
        }

        // The recipient of the message must verify that it was delivered.
        if (!Objects.equals(message.getRecipientUserId(), currentUser.getId())) {
            return new ResponseBuilder()
                    .withError("Failed to verify message.")
                    .withStatus(HttpStatus.PRECONDITION_FAILED)
                    .toResponseEntity();
        }

        message.setMessageStatus(MessageStatus.DELIVERED);
        Message updatedMessage = messageService.update(message);

        return new ResponseBuilder()
                .withBody(updatedMessage)
                .toResponseEntity();
    }
}
