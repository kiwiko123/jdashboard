package com.kiwiko.services.featureFlags.internal;

import com.kiwiko.services.featureFlags.dto.FeatureFlag;
import com.kiwiko.services.featureFlags.internal.database.FeatureFlagEntity;
import com.kiwiko.webapp.mvc.persistence.impl.VersionedEntityMapper;

public class FeatureFlagEntityMapper extends VersionedEntityMapper<FeatureFlagEntity, FeatureFlag> {

    @Override
    protected Class<FeatureFlagEntity> getEntityType() {
        return FeatureFlagEntity.class;
    }

    @Override
    protected Class<FeatureFlag> getDTOType() {
        return FeatureFlag.class;
    }
}
