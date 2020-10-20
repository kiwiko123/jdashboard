package com.kiwiko.webapp.messages.chatroom.internal;

import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;

import java.util.Collection;
import java.util.List;

public class ChatroomMessageEntityDAO extends MessageEntityDAO {

    public List<MessageEntity> getBetween(long senderUserId, Collection<Long> recipientUserIds) {
        return super.getBetween(senderUserId, recipientUserIds, MessageType.CHATROOM);
    }
}
