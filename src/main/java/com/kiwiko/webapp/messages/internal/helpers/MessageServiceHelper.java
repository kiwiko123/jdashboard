package com.kiwiko.webapp.messages.internal.helpers;

import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessagePreview;
import com.kiwiko.webapp.messages.data.MessagePreviewUser;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.data.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessageServiceHelper {

    @Inject
    private UserService userService;

    public List<MessagePreview> makeMessagePreviews(long userId, Collection<Message> relatedMessages) {
        Map<Long, Message> mostRecentMessageByOtherUserId = getMostRecentMessageByOtherUserId(userId, relatedMessages);
        Map<Long, User> usersById = userService.getByIds(mostRecentMessageByOtherUserId.keySet()).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<MessagePreview> previews = new ArrayList<>();
        mostRecentMessageByOtherUserId.forEach((otherUserId, message) -> {
            User user = usersById.get(otherUserId);
            MessagePreviewUser previewUser = new MessagePreviewUser();
            previewUser.setUserId(user.getId());
            previewUser.setUserName(user.getUsername());

            MessagePreview preview = new MessagePreview();
            preview.setMessage(message);

            // TODO support group chats?
            preview.setUsers(Collections.singletonList(previewUser));

            previews.add(preview);
        });

        return previews;
    }

    private Map<Long, Message> getMostRecentMessageByOtherUserId(long userId, Collection<Message> relatedMessages) {
        Map<Long, Message> messagesByOtherUserId = new HashMap<>();
        for (Message message : relatedMessages) {
            Long senderUserId = message.getSenderUserId();
            Long recipientUserId = message.getRecipientUserId();
            boolean userIsSender = Objects.equals(senderUserId, userId);
            boolean userIsRecipient = Objects.equals(recipientUserId, userId);

            if (!(userIsSender || userIsRecipient)) {
                continue;
            }

            Long otherUserId = userIsSender ? recipientUserId : senderUserId;
            messagesByOtherUserId.merge(otherUserId, message, this::getMostRecentlySent);
        }

        return messagesByOtherUserId;
    }

    private Message getMostRecentlySent(Message... messages) {
        return Arrays.stream(messages)
                .max(Comparator.comparing(Message::getSentDate))
                .orElse(null);
    }
}
