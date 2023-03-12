package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Instant;

@Getter
@Setter
public class ServiceRequestKey extends DataEntityDTO {
    private String scope;
    private String serviceClientName;
    private @Nullable String description;
    private Instant createdDate;
    private @Nullable Long createdByUserId;
    private Instant expirationDate;
    private String requestToken;
}
