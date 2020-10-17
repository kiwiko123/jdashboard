package com.kiwiko.webapp.messages.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.api.exceptions.MessageException;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageEntityService implements MessageService {

    @Inject private MessageEntityFieldMapper messageMapper;
    @Inject private MessageEntityDAO messageEntityDAO;
    @Inject private LogService logService;

    @Transactional(readOnly = true)
    @Override
    public List<Message> getBetween(long senderUserId, Collection<Long> recipientUserIds) {
        Set<Long> filteredRecipientUserIds = new HashSet<>(recipientUserIds);
        return messageEntityDAO.getBetween(senderUserId, filteredRecipientUserIds).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Message send(Message message) {
        List<Message> messages = Collections.singletonList(message);
        return send(messages).stream()
                .findFirst()
                .orElseThrow(() -> new MessageException("Failed to send message"));
    }

    @Transactional
    @Override
    public List<Message> send(List<Message> messages) {
        List<Message> results = new ArrayList<>();
        for (Message message : messages) {
            MessageEntity entity = messageMapper.toEntity(message);

            try {
                entity = messageEntityDAO.save(entity);
            } catch (Exception e) {
                logService.error(String.format("Failed to send message %s", entity.toString()), e);
                entity.setMessageStatus(MessageStatus.FAILURE);
            }

            Message result = messageMapper.toDTO(entity);
            results.add(result);
        }

        return results;
    }
}
