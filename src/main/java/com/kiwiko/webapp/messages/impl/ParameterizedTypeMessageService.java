package com.kiwiko.webapp.messages.impl;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.api.exceptions.MessageException;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import com.kiwiko.webapp.messages.internal.helpers.MessageServiceHelper;
import com.kiwiko.webapp.users.api.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ParameterizedTypeMessageService implements MessageService {

    @Inject protected MessageEntityFieldMapper messageMapper;
    @Inject private MessageEntityDAO messageEntityDAO;
    @Inject private MessageServiceHelper messageServiceHelper;
    @Inject private LogService logService;
    @Inject private UserService userService;

    protected abstract MessageType getMessageType();

    @Transactional(readOnly = true)
    @Override
    public List<Message> getBetween(GetBetweenParameters parameters) {
        MessageType messageType = getMessageType();
        return messageEntityDAO.getBetween(parameters, messageType).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessagePreview> getMessagePreviewsForUser(long userId) {
        List<Message> relatedMessages = messageEntityDAO.getRelatedToUser(userId).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());

        return messageServiceHelper.makeMessagePreviews(userId, relatedMessages);
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
    public List<Message> send(List<Message> messages) {
        List<Message> results = new ArrayList<>();
        for (Message message : messages) {
            Message result = new Message();

            try {
                message.setSentDate(Instant.now());
                message.setMessageStatus(MessageStatus.SENT);
                result = create(message);
            } catch (Exception e) {
                logService.error(String.format("Failed to send message %s", message.toString()), e);
                result.setMessageStatus(MessageStatus.FAILURE);
            }

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
        entity.setMessageType(getMessageType());
        entity = messageEntityDAO.save(entity);
        return messageMapper.toDTO(entity);
    }

    @Transactional
    @Override
    public Message update(Message message) {
        MessageEntity entity = messageMapper.toEntity(message);
        entity.setMessageType(getMessageType());
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
