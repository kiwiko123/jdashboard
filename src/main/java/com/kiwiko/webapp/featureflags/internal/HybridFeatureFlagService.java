package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntity;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntityDAO;
import com.kiwiko.webapp.mvc.persistence.crud.api.CreateReadUpdateDeleteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HybridFeatureFlagService
        extends CreateReadUpdateDeleteService<FeatureFlagEntity, FeatureFlag, FeatureFlagEntityDAO, FeatureFlagEntityMapper>
        implements FeatureFlagService {

    @Inject private FeatureFlagEntityDAO featureFlagEntityDAO;
    @Inject private FeatureFlagEntityMapper featureFlagEntityMapper;

    @Override
    protected FeatureFlagEntityDAO getDataFetcher() {
        return featureFlagEntityDAO;
    }

    @Override
    protected FeatureFlagEntityMapper getMapper() {
        return featureFlagEntityMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getByName(String name) {
        return featureFlagEntityDAO.getByName(name).map(featureFlagEntityMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FeatureFlag> getForUser(String name, long userId) {
        return featureFlagEntityDAO.getForUser(name, userId).map(featureFlagEntityMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<FeatureFlag> getAll() {
        return featureFlagEntityDAO.getAll().stream()
                .map(featureFlagEntityMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
