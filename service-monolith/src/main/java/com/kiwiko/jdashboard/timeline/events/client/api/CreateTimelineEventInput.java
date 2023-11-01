package com.kiwiko.jdashboard.timeline.events.client.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTimelineEventInput {
    private @Nonnull String eventName;
    private @Nullable String eventKey;
    private @Nullable String metadata;
    private @Nonnull Long currentUserId;
}
