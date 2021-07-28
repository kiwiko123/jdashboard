package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntityDataFetcher;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FeatureFlagEntityService implements FeatureFlagService {

    @Inject private FeatureFlagEntityDataFetcher dataFetcher;
    @Inject private FeatureFlagEntityMapper entityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

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

    @Override
    public Optional<FeatureFlag> read(long id) {
        return crudExecutor.read(id, dataFetcher, entityMapper);
    }

    @Override
    public <R extends FeatureFlag> FeatureFlag create(R obj) {
        return crudExecutor.create(obj, dataFetcher, entityMapper);
    }

    @Override
    public <R extends FeatureFlag> FeatureFlag update(R obj) {
        return crudExecutor.update(obj, dataFetcher, entityMapper);
    }

    @Override
    public void delete(long id) {
        crudExecutor.delete(id, dataFetcher);
    }

    @Override
    public FeatureFlag merge(FeatureFlag featureFlag) {
        return crudExecutor.merge(featureFlag, dataFetcher, entityMapper);
    }
}
