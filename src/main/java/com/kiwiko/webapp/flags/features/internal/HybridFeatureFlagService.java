package com.kiwiko.webapp.flags.features.internal;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.webapp.flags.features.api.FeatureFlagService;
import com.kiwiko.webapp.flags.features.dto.FeatureFlagStatus;
import com.kiwiko.webapp.flags.features.dto.FeatureFlagUserScope;
import com.kiwiko.webapp.flags.features.dto.FeatureFlag;
import com.kiwiko.webapp.flags.features.internal.database.FeatureFlagEntity;
import com.kiwiko.webapp.flags.features.internal.database.FeatureFlagEntityDAO;
import com.kiwiko.webapp.mvc.persistence.crud.api.CacheableCreateReadUpdateDeleteService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.function.Supplier;

public class HybridFeatureFlagService
        extends CacheableCreateReadUpdateDeleteService<FeatureFlagEntity, FeatureFlag, FeatureFlagEntityDAO, FeatureFlagEntityMapper>
        implements FeatureFlagService {

    @Inject private FeatureFlagEntityDAO featureFlagEntityDAO;
    @Inject private FeatureFlagEntityMapper featureFlagEntityMapper;
    @Inject private FeatureFlagCacheHelper cacheHelper;
    @Inject private ObjectCache objectCache;

    @Override
    protected FeatureFlagEntityDAO dataAccessObject() {
        return featureFlagEntityDAO;
    }

    @Override
    protected FeatureFlagEntityMapper mapper() {
        return featureFlagEntityMapper;
    }

    @Override
    protected ObjectCache cache() {
        return objectCache;
    }

    @Override
    protected TemporalAmount cacheDuration() {
        return FeatureFlagCacheHelper.DEFAULT_FLAG_CACHE_DURATION;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getByName(String name) {
        String key = cacheHelper.makeFlagCacheKey(name, null);
        Supplier<Optional<FeatureFlag>> fetchFlag = () -> featureFlagEntityDAO.getByName(name).map(featureFlagEntityMapper::toDTO);
        return obtain(key, fetchFlag);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getForUser(String name, long userId) {
        String key = cacheHelper.makeFlagCacheKey(name, userId);
        Supplier<Optional<FeatureFlag>> fetchFlag = () -> featureFlagEntityDAO.getForUser(name, userId).map(featureFlagEntityMapper::toDTO);
        return obtain(key, fetchFlag);
    }

    @Transactional(readOnly = true)
    public boolean resolve(String name, @Nullable Long userId) {
        Optional<FeatureFlag> flag;
        if (userId == null) {
            flag = getByName(name).filter(f -> FeatureFlagUserScope.PUBLIC.getId().equalsIgnoreCase(f.getUserScope()));
        } else {
            flag = getForUser(name, userId);
        }

        return flag.map(FeatureFlag::getStatus)
                .map(FeatureFlagStatus.ENABLED.getId()::equalsIgnoreCase)
                .orElse(false);
    }
}
