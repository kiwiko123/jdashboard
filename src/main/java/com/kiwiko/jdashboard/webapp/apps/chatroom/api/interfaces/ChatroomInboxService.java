package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox.ChatroomInboxFeed;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.GetInboxFeedParameters;

public interface ChatroomInboxService {

    ChatroomInboxFeed getInboxFeed(GetInboxFeedParameters parameters);
}
