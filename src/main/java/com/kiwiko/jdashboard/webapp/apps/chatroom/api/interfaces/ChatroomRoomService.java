package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms.ChatroomMessageFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms.GetMessageFeedParameters;

public interface ChatroomRoomService {

    ChatroomMessageFeed getMessageFeed(GetMessageFeedParameters parameters);
}
