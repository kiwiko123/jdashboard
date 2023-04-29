package com.kiwiko.jdashboard.clients.featureflags.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FeatureFlagUserAssociation extends SoftDeletableDataEntityDTO {
    private Long featureFlagId;
    private Long userId;
}
