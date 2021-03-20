package com.kiwiko.webapp.flags.features.internal;

import com.kiwiko.webapp.flags.features.dto.FeatureFlag;
import com.kiwiko.webapp.flags.features.internal.database.FeatureFlagEntity;
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
