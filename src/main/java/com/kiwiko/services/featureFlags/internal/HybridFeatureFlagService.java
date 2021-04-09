package com.kiwiko.services.featureFlags.internal;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.services.featureFlags.api.FeatureFlagService;
import com.kiwiko.services.featureFlags.dto.FeatureFlag;
import com.kiwiko.services.featureFlags.internal.database.FeatureFlagEntity;
import com.kiwiko.services.featureFlags.internal.database.FeatureFlagEntityDAO;
import com.kiwiko.webapp.mvc.persistence.crud.api.CacheableCreateReadUpdateDeleteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HybridFeatureFlagService
        extends CacheableCreateReadUpdateDeleteService<FeatureFlagEntity, FeatureFlag, FeatureFlagEntityDAO, FeatureFlagEntityMapper>
        implements FeatureFlagService {

    @Inject private FeatureFlagEntityDAO featureFlagEntityDAO;
    @Inject private FeatureFlagEntityMapper featureFlagEntityMapper;
    @Inject private FeatureFlagCacheHelper cacheHelper;
    @Inject private ObjectCache objectCache;

    @Override
    protected FeatureFlagEntityDAO getDataFetcher() {
        return featureFlagEntityDAO;
    }

    @Override
    protected FeatureFlagEntityMapper getMapper() {
        return featureFlagEntityMapper;
    }

    @Override
    protected ObjectCache getCache() {
        return objectCache;
    }

    @Override
    protected TemporalAmount getCacheDuration() {
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
    @Override
    public Set<FeatureFlag> getAll() {
        return featureFlagEntityDAO.getAll().stream()
                .map(featureFlagEntityMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
