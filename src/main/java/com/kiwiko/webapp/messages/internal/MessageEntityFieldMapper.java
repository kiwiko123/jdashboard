package com.kiwiko.webapp.messages.internal;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;

import javax.inject.Singleton;

@Singleton
public class MessageEntityFieldMapper extends EntityMapper<MessageEntity, Message> {

    @Override
    protected Class<MessageEntity> getEntityType() {
        return MessageEntity.class;
    }

    @Override
    protected Class<Message> getDTOType() {
        return Message.class;
    }
}
