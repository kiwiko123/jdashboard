package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class IncomingApplicationRequestLog extends DataEntityDTO {
    private String uri;
    private @Nullable String remoteHost;
    private @Nullable String ipAddress;
    private Instant requestDate;
    private @Nullable Long userId;
}
