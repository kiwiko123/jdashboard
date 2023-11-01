package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;

public class ClientSession extends DataEntityDTO {

    private Instant startTime;
    private @Nullable Instant endTime;

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @Nullable
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Instant endTime) {
        this.endTime = endTime;
    }
}
