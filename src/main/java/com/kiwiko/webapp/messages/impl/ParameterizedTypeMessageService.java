package com.kiwiko.webapp.messages.impl;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import com.kiwiko.webapp.messages.internal.helpers.MessageServiceHelper;
import com.kiwiko.webapp.mvc.persistence.crud.api.CreateReadUpdateDeleteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParameterizedTypeMessageService
        extends CreateReadUpdateDeleteService<MessageEntity, Message, MessageEntityDAO, MessageEntityFieldMapper>
        implements MessageService {

    @Inject protected MessageEntityFieldMapper messageMapper;
    @Inject private MessageEntityDAO messageEntityDAO;
    @Inject private MessageServiceHelper messageServiceHelper;
    @Inject private Logger logger;

    protected abstract MessageType getMessageType();

    @Override
    protected MessageEntityDAO getDataFetcher() {
        return messageEntityDAO;
    }

    @Override
    protected MessageEntityFieldMapper getMapper() {
        return messageMapper;
    }

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
        Message result = new Message();
        try {
            message.setSentDate(Instant.now());
            message.setMessageStatus(MessageStatus.SENT);
            result = create(message);
        } catch (Exception e) {
            logger.error(String.format("Failed to send message %s", message.toString()), e);
            result.setMessageStatus(MessageStatus.FAILURE);
        }

        return result;
    }

    @Transactional
    public List<Message> send(List<Message> messages) {
        return messages.stream()
                .map(this::send)
                .collect(Collectors.toList());
    }
}
