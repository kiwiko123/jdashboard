package com.kiwiko.webapp.messages.impl;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.api.exceptions.MessageException;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class ParameterizedTypeMessageService implements MessageService {

    @Inject protected MessageEntityFieldMapper messageMapper;
    @Inject private MessageEntityDAO messageEntityDAO;
    @Inject private LogService logService;

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

    @Transactional(readOnly = true)
    @Override
    public Optional<Message> get(long messageId) {
        return messageEntityDAO.getById(messageId)
                .map(messageMapper::toDTO);
    }

    @Transactional
    @Override
    public Message create(Message message) {
        MessageEntity entity = messageMapper.toEntity(message);
        entity = messageEntityDAO.save(entity);
        return messageMapper.toDTO(entity);
    }

    @Transactional
    @Override
    public Message update(Message message) {
        MessageEntity entity = messageMapper.toEntity(message);
        entity = messageEntityDAO.save(entity);
        return messageMapper.toDTO(entity);
    }

    @Transactional
    @Override
    public void delete(long messageId) throws MessageException {
        MessageEntity entity = messageEntityDAO.getProxyById(messageId)
                .orElseThrow(() -> new MessageException(String.format("Failed to find message with ID %d", messageId)));
        messageEntityDAO.delete(entity);
    }
}
