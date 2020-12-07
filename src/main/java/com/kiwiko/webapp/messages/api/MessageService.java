package com.kiwiko.webapp.messages.api;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;

import java.util.List;
import java.util.Optional;

public interface MessageService extends CreateReadUpdateDeleteAPI<Message> {

    List<Message> getBetween(GetBetweenParameters parameters);
    List<MessagePreview> getMessagePreviewsForUser(long userId);

    Message send(Message message);
}
