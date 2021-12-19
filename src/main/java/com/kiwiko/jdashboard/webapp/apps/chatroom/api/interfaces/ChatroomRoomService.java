package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomMessageFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetMessageFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageRequest;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.SendMessageResponse;

public interface ChatroomRoomService {

    ChatroomMessageFeed getMessageFeed(GetMessageFeedParameters parameters);

    SendMessageResponse sendMessage(SendMessageRequest request);
}
