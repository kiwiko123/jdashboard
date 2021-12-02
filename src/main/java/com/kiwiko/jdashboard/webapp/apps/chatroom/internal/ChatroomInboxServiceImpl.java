package com.kiwiko.jdashboard.webapp.apps.chatroom.internal;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxItem;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomUserService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatroomInboxServiceImpl implements ChatroomInboxService {

    @Inject private ChatroomMessageRoomService chatroomMessageRoomService;
    @Inject private ChatroomMessageRoomUserService chatroomMessageRoomUserService;

    @Override
    public ChatroomInboxFeed getInboxFeed(GetInboxFeedParameters parameters) {
        Objects.requireNonNull(parameters, "Input parameters required");
        Objects.requireNonNull(parameters.getUserId(), "User ID is required");

        Set<ChatroomMessageRoom> roomsForUser = chatroomMessageRoomService.getRoomsForUser(parameters.getUserId());
        Set<Long> chatroomMessageRoomIds = roomsForUser.stream()
                .map(ChatroomMessageRoom::getId)
                .collect(Collectors.toSet());

        Map<Long, Set<ChatroomMessageRoomUser>> roomUsersByRoomId = chatroomMessageRoomUserService.getByRoomIds(chatroomMessageRoomIds).stream()
                .collect(Collectors.groupingBy(ChatroomMessageRoomUser::getChatroomMessageRoomId, Collectors.toSet()));

        List<ChatroomInboxItem> inboxItems = new LinkedList<>();
        for (ChatroomMessageRoom room : roomsForUser) {
            Set<ChatroomMessageRoomUser> usersInRoom = roomUsersByRoomId.getOrDefault(room.getId(), Collections.emptySet());
            Set<String> usernames = usersInRoom.stream()
                    .map(ChatroomMessageRoomUser::getUserId)
                    .map(Objects::toString)
                    .collect(Collectors.toSet());

            ChatroomInboxItem item = new ChatroomInboxItem();
            item.setRoom(room);
            item.setUsernames(usernames);

            inboxItems.add(item);
        }

        ChatroomInboxFeed inboxFeed = new ChatroomInboxFeed();
        inboxFeed.setInboxItems(inboxItems);

        return inboxFeed;
    }
}
