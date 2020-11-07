package com.kiwiko.webapp.messages.api;

import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> getBetween(GetBetweenParameters parameters);
    List<MessagePreview> getMessagePreviewsForUser(long userId);

    Message send(Message message);

    Optional<Message> get(long messageId);
    Message create(Message message);
    Message update(Message message);
    void delete(long messageId);
}
