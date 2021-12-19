package com.kiwiko.jdashboard.webapp.apps.chatroom.internal;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessage;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomMessageFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomRoomMessage;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetMessageFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageResponse;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.exceptions.ChatroomRuntimeException;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.parameters.GetMessagesForRoomParameters;
import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;

import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChatroomRoomServiceImpl implements ChatroomRoomService {

    @Inject private ChatroomMessageService chatroomMessageService;
    @Inject private ChatroomMessageRoomService chatroomMessageRoomService;
    @Inject private UserClient userClient;

    @Override
    public ChatroomMessageFeed getMessageFeed(GetMessageFeedParameters parameters) {
        Objects.requireNonNull(parameters, "GetMessageFeedParameters required");
        Objects.requireNonNull(parameters.getRoomId(), "Room ID is required");
        Objects.requireNonNull(parameters.getUserId(), "User ID is required");

        GetMessagesForRoomParameters getMessagesForRoomParameters = new GetMessagesForRoomParameters();
        getMessagesForRoomParameters.setChatroomMessageRoomId(parameters.getRoomId());
        getMessagesForRoomParameters.setMaxMessagesToFetch(parameters.getMaxMessagesToFetch());
        getMessagesForRoomParameters.setIncludeRemovedMessages(false);
        List<ChatroomMessage> chatroomMessages = chatroomMessageService.getMessagesByRoomId(getMessagesForRoomParameters);

        Set<Long> userIds = chatroomMessages.stream()
                .map(ChatroomMessage::getSenderUserId)
                .collect(Collectors.toSet());
        GetUsersQuery getUsersQuery = GetUsersQuery.newBuilder()
                .setUserIds(userIds)
                .build();
        Map<Long, User> usersById = userClient.getByQuery(getUsersQuery).getUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<ChatroomRoomMessage> chatroomRoomMessages = new LinkedList<>();
        for (ChatroomMessage message : chatroomMessages) {
            String userName = Optional.ofNullable(usersById.get(message.getSenderUserId()))
                    .map(User::getUsername)
                    .orElse(null);
            Objects.requireNonNull(userName, "Sender user display name is required");

            ChatroomRoomMessage chatroomRoomMessage = new ChatroomRoomMessage();
            chatroomRoomMessage.setChatroomMessage(message);
            chatroomRoomMessage.setSenderDisplayName(userName);
            chatroomRoomMessages.add(chatroomRoomMessage);
        }

        ChatroomMessageFeed chatroomMessageFeed = new ChatroomMessageFeed();
        chatroomMessageFeed.setMessages(chatroomRoomMessages);
        return chatroomMessageFeed;
    }

    @Override
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        Objects.requireNonNull(request, "Request required");
        Objects.requireNonNull(request.getChatroomMessageRoomId(), "Chatroom message room ID required");
        Objects.requireNonNull(request.getSenderUserId(), "Sender user ID required");

        // Validate that the user is in the room.
        boolean isSendingUserInRoom = chatroomMessageRoomService.getRoomsForUsers(Collections.singleton(request.getSenderUserId())).stream()
                .map(ChatroomMessageRoom::getId)
                .anyMatch(request.getChatroomMessageRoomId()::equals);
        if (!isSendingUserInRoom) {
            throw new ChatroomRuntimeException(String.format("User ID %d is not in chatroom message room %d", request.getSenderUserId(), request.getChatroomMessageRoomId()));
        }

        // Send the message.
        ChatroomMessage message = new ChatroomMessage();
        message.setChatroomMessageRoomId(request.getChatroomMessageRoomId());
        message.setSenderUserId(request.getSenderUserId());
        message.setMessage(request.getMessage());

        ChatroomMessage sentMessage = chatroomMessageService.create(message);

        SendMessageResponse response = new SendMessageResponse();
        response.setSentMessage(sentMessage);

        return response;
    }
}
