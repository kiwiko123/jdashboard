package com.kiwiko.webapp.messages.api.queries.data;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public class GetBetweenParameters {

    private Long senderUserId;
    private Collection<Long> recipientUserIds;
    private @Nullable Instant minimumSentDate;
    private @Nullable Integer maxResults;

    public Long getSenderUserId() {
        return senderUserId;
    }

    public GetBetweenParameters withSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
        return this;
    }

    public Collection<Long> getRecipientUserIds() {
        return recipientUserIds;
    }

    public GetBetweenParameters withRecipientUserIds(Collection<Long> recipientUserIds) {
        this.recipientUserIds = recipientUserIds;
        return this;
    }

    public Optional<Instant> getMinimumSentDate() {
        return Optional.ofNullable(minimumSentDate);
    }

    public GetBetweenParameters withMinimumSentDate(@Nullable Instant minimumSentDate) {
        this.minimumSentDate = minimumSentDate;
        return this;
    }

    public Optional<Integer> getMaxResults() {
        return Optional.ofNullable(maxResults);
    }

    public GetBetweenParameters withMaxResults(@Nullable Integer maxResults) {
        this.maxResults = maxResults;
        return this;
    }
}
