package com.kiwiko.jdashboard.webapp.application.events.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "application_events")
public class ApplicationEventEntity implements LongDataEntity, SoftDeletable {
    private Long id;
    private String eventType;
    private @Nullable String eventKey;
    private @Nullable String metadata;
    private boolean isRemoved;
    private Instant createdDate;

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

    @Column(name = "event_type", nullable = false)
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Nullable
    @Column(name = "event_key")
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@Nullable String eventKey) {
        this.eventKey = eventKey;
    }

    @Nullable
    @Column(name = "metadata")
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(@Nullable String metadata) {
        this.metadata = metadata;
    }

    @Override
    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
