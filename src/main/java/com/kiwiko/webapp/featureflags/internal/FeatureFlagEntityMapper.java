package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntity;
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
