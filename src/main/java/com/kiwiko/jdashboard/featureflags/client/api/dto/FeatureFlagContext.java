package com.kiwiko.jdashboard.featureflags.client.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeatureFlagContext extends DataEntityDTO {
    private Long featureFlagId;
    private String scope;
    private @Nullable Long userId;
    private String flagStatus;
    private @Nullable String flagValue;
    private Instant startDate;
    private @Nullable Instant endDate;
}
