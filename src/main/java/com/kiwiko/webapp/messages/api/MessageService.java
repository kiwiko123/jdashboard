package com.kiwiko.webapp.messages.api;

import com.kiwiko.webapp.messages.data.Message;

import java.util.Collection;
import java.util.List;

public interface MessageService {

    List<Message> getBetween(long senderUserId, Collection<Long> recipientUserIds);

    Message send(Message message);
    List<Message> send(List<Message> messages);
}
