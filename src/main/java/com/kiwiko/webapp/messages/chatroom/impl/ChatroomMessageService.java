package com.kiwiko.webapp.messages.chatroom.impl;

import com.kiwiko.webapp.messages.chatroom.internal.ChatroomMessageEntityDAO;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.impl.ParameterizedTypeMessageService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ChatroomMessageService extends ParameterizedTypeMessageService {

    @Inject private ChatroomMessageEntityDAO messageEntityDAO;

    @Transactional(readOnly = true)
    @Override
    public List<Message> getBetween(long senderUserId, Collection<Long> recipientUserIds) {
        return messageEntityDAO.getBetween(senderUserId, new HashSet<>(recipientUserIds)).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
