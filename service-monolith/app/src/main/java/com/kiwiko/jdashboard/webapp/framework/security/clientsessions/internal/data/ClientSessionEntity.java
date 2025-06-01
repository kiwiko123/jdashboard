package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.data;

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
@Table(name = "client_sessions")
public class ClientSessionEntity implements LongDataEntity {

    private Long id;
    private Instant startTime;
    private @Nullable Instant endTime;

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

    @Column(name = "start_time", nullable = false)
    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @Nullable
    @Column(name = "end_time")
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Instant endTime) {
        this.endTime = endTime;
    }
}
