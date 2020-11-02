package com.kiwiko.webapp.messages.internal;

import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntity;
import com.kiwiko.webapp.mvc.persistence.impl.VersionedEntityMapper;

import javax.inject.Singleton;

@Singleton
public class MessageEntityFieldMapper extends VersionedEntityMapper<MessageEntity, Message> {

    @Override
    protected Class<MessageEntity> getEntityType() {
        return MessageEntity.class;
    }

    @Override
    protected Class<Message> getDTOType() {
        return Message.class;
    }
}
