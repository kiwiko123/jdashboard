package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntity;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FeatureFlagEntityService implements FeatureFlagService {

    @Inject private FeatureFlagEntityDAO dataFetcher;
    @Inject private FeatureFlagEntityMapper entityMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getByName(String name) {
        return dataFetcher.getByName(name).map(entityMapper::toTargetType);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getForUser(String name, long userId) {
        return dataFetcher.getForUser(name, userId).map(entityMapper::toTargetType);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<FeatureFlag> getAll() {
        return dataFetcher.getAll().stream()
                .map(entityMapper::toTargetType)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> read(long id) {
        return dataFetcher.getById(id).map(entityMapper::toTargetType);
    }

    @Transactional
    @Override
    public <R extends FeatureFlag> FeatureFlag create(R obj) {
        FeatureFlagEntity entity = entityMapper.toSourceType(obj);
        entity = dataFetcher.save(entity);
        return entityMapper.toTargetType(entity);
    }

    @Override
    public <R extends FeatureFlag> FeatureFlag update(R obj) {
        FeatureFlagEntity entity = entityMapper.toSourceType(obj);
        entity = dataFetcher.save(entity);
        return entityMapper.toTargetType(entity);
    }

    @Override
    public void delete(long id) {
        FeatureFlagEntity entity = dataFetcher.getById(id).orElseThrow(() -> new PersistenceException("No entity found"));
        dataFetcher.delete(entity);
    }
}
