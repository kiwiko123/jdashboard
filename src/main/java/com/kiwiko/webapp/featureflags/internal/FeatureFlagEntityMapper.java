package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntity;

public class FeatureFlagEntityMapper extends EntityMapper<FeatureFlagEntity, FeatureFlag> {

    @Override
    protected Class<FeatureFlagEntity> getEntityType() {
        return FeatureFlagEntity.class;
    }

    @Override
    protected Class<FeatureFlag> getDTOType() {
        return FeatureFlag.class;
    }
}
