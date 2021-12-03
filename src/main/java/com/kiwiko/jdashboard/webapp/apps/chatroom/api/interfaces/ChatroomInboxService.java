package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.NewChatroom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.CreateChatroomFormInput;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.exceptions.ChatroomMessageRoomAlreadyExistsException;

public interface ChatroomInboxService {

    ChatroomInboxFeed getInboxFeed(GetInboxFeedParameters parameters);

    NewChatroom createRoomFromForm(CreateChatroomFormInput input) throws ChatroomMessageRoomAlreadyExistsException;
}
