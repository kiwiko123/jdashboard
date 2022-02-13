package com.kiwiko.jdashboard.webapp.apps.chatroom.internal;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxItem;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxItemUserData;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.NewChatroom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.CreateChatroomFormInput;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomUserService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.exceptions.ChatroomMessageRoomAlreadyExistsException;
import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.library.lang.util.TypedObjects;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChatroomInboxServiceImpl implements ChatroomInboxService {

    @Inject private ChatroomMessageRoomService chatroomMessageRoomService;
    @Inject private ChatroomMessageRoomUserService chatroomMessageRoomUserService;
    @Inject private UniqueIdentifierService uniqueIdentifierService;
    @Inject private UserClient userClient;

    @Override
    public ChatroomInboxFeed getInboxFeed(GetInboxFeedParameters parameters) {
        Objects.requireNonNull(parameters, "Input parameters required");
        Objects.requireNonNull(parameters.getUserId(), "User ID is required");

        Set<ChatroomMessageRoom> roomsForUser = chatroomMessageRoomService.getRoomsForUsers(Collections.singleton(parameters.getUserId()));
        List<ChatroomInboxItem> inboxItems = getInboxItemsForFeed(parameters, roomsForUser);

        ChatroomInboxFeed inboxFeed = new ChatroomInboxFeed();
        inboxFeed.setInboxItems(inboxItems);

        return inboxFeed;
    }

    @Override
    public NewChatroom createRoomFromForm(CreateChatroomFormInput input) throws ChatroomMessageRoomAlreadyExistsException {
        Objects.requireNonNull(input, "Input is required");

        Set<User> allUsers = getUsersForCreateChatroomForm(input);

        // Validate that a room doesn't already exist.
        Set<Long> allUserIds = allUsers.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        Set<ChatroomMessageRoom> existingRooms = getExistingRoomsForUsers(allUserIds);
        if (!existingRooms.isEmpty()) {
            throw new ChatroomMessageRoomAlreadyExistsException(String.format("Message rooms %s already exist for user IDs %s", existingRooms, allUserIds));
        }

        // Create a new room.
        ChatroomMessageRoom messageRoom = chatroomMessageRoomService.create(new ChatroomMessageRoom());
        String roomUuid = chatroomMessageRoomService.getRoomUuid(messageRoom.getId());

        // Add all users into the newly-created room.
        Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers = allUsers.stream()
                .map(User::getId)
                .map(userId -> {
                    ChatroomMessageRoomUser messageRoomUser = new ChatroomMessageRoomUser();
                    messageRoomUser.setUserId(userId);
                    messageRoomUser.setChatroomMessageRoomId(messageRoom.getId());
                    return messageRoomUser;
                })
                .map(chatroomMessageRoomUserService::create)
                .collect(Collectors.toSet());

        NewChatroom createdChatroom = new NewChatroom();
        createdChatroom.setChatroomMessageRoom(messageRoom);
        createdChatroom.setChatroomMessageRoomUuid(roomUuid);
        createdChatroom.setChatroomMessageRoomUsers(chatroomMessageRoomUsers);

        return createdChatroom;
    }

    private List<ChatroomInboxItem> getInboxItemsForFeed(
            GetInboxFeedParameters getInboxFeedParameters,
            Collection<ChatroomMessageRoom> roomsForUser) {
        Set<Long> chatroomMessageRoomIds = roomsForUser.stream()
                .map(ChatroomMessageRoom::getId)
                .collect(Collectors.toSet());

        Set<ChatroomMessageRoomUser> messageRoomUsers = chatroomMessageRoomUserService.getByRoomIds(chatroomMessageRoomIds);
        Map<Long, Set<ChatroomMessageRoomUser>> roomUsersByRoomId = messageRoomUsers.stream()
                .collect(Collectors.groupingBy(ChatroomMessageRoomUser::getChatroomMessageRoomId, Collectors.toSet()));

        Set<Long> userIds = messageRoomUsers.stream()
                .map(ChatroomMessageRoomUser::getUserId)
                .collect(Collectors.toSet());
        GetUsersQuery getUsersQuery = GetUsersQuery.newBuilder()
                .setUserIds(userIds)
                .build();
        Map<Long, User> usersById = userClient.getByQuery(getUsersQuery).getUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return roomsForUser.stream()
                .map(room -> createInboxItem(getInboxFeedParameters, room, roomUsersByRoomId, usersById))
                .collect(Collectors.toList());
    }

    private Set<ChatroomMessageRoom> getExistingRoomsForUsers(Collection<Long> userIds) {
        Set<ChatroomMessageRoom> existingRooms = chatroomMessageRoomService.getRoomsForUsers(userIds);
        Map<Long, Set<Long>> userIdsByRoomId = new HashMap<>();

        for (ChatroomMessageRoom room : existingRooms) {
            Set<Long> userIdsInRoom = chatroomMessageRoomUserService.getByRoomIds(Collections.singleton(room.getId())).stream()
                    .map(ChatroomMessageRoomUser::getUserId)
                    .collect(Collectors.toSet());
            userIdsByRoomId.put(room.getId(), userIdsInRoom);
        }

        return existingRooms.stream()
                .filter((room) -> {
                   Set<Long> userIdsInRoom = userIdsByRoomId.getOrDefault(room.getId(), Collections.emptySet());
                   return Objects.equals(userIds, userIdsInRoom);
                })
                .collect(Collectors.toSet());
    }

    private ChatroomInboxItem createInboxItem(
            GetInboxFeedParameters getInboxFeedParameters,
            ChatroomMessageRoom chatroomMessageRoom,
            Map<Long, Set<ChatroomMessageRoomUser>> roomUsersByRoomId,
            Map<Long, User> usersById) {
        Set<ChatroomMessageRoomUser> usersInRoom = roomUsersByRoomId.getOrDefault(chatroomMessageRoom.getId(), Collections.emptySet());
        Set<ChatroomInboxItemUserData> userData = usersInRoom.stream()
                .map(ChatroomMessageRoomUser::getUserId)
                .filter(userId -> TypedObjects.notEquals(userId, getInboxFeedParameters.getUserId()))
                .map(usersById::get)
                .filter(Objects::nonNull)
                .map(ChatroomInboxItemUserData::fromUser)
                .collect(Collectors.toSet());
        String roomUuid = chatroomMessageRoomService.getRoomUuid(chatroomMessageRoom.getId()); // TODO fix N+1 fetch

        ChatroomInboxItem item = new ChatroomInboxItem();
        item.setRoom(chatroomMessageRoom);
        item.setRoomUuid(roomUuid);
        item.setUsers(userData);

        return item;
    }

    private Set<User> getUsersForCreateChatroomForm(CreateChatroomFormInput input) {
        GetUsersQuery getRecipientsQuery = GetUsersQuery.newBuilder()
                .setUsernames(input.getRecipientUsernames())
                .build();

        Set<User> result = userClient.getByQuery(getRecipientsQuery).getUsers();
        userClient.getById(input.getUserId()).getUser()
                .ifPresent(result::add);

        return result;
    }
}
