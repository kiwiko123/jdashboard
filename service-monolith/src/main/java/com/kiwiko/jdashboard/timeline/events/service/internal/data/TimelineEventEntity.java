package com.kiwiko.jdashboard.timeline.events.service.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "timeline_events")
public class TimelineEventEntity implements LongDataEntity {
    private Long id;
    private String eventName;
    private @Nullable String eventKey;
    private @Nullable String metadata;
    private Instant createdDate;
    private Long createdByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "event_name", nullable = false)
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Column(name = "event_key")
    @Nullable
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@Nullable String eventKey) {
        this.eventKey = eventKey;
    }

    @Column(name = "metadata")
    @Nullable
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(@Nullable String metadata) {
        this.metadata = metadata;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "created_by_user_id", nullable = false)
    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
