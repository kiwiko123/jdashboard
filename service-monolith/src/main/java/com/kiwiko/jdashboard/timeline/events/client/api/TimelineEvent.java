package com.kiwiko.jdashboard.timeline.events.client.api;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class TimelineEvent extends DataEntityDTO {
    private String eventName;
    private @Nullable String eventKey;
    private @Nullable String metadata;
    private Instant createdDate;
    private Long createdByUserId;
}
