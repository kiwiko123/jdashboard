package com.kiwiko.jdashboard.services.featureflags.internal;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlagUserAssociation;
import com.kiwiko.jdashboard.services.featureflags.internal.data.FeatureFlagUserAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class FeatureFlagUserAssociationEntityService {
    @Inject private FeatureFlagUserAssociationEntityDataFetcher dataFetcher;
    @Inject private FeatureFlagUserAssociationEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    public Optional<FeatureFlagUserAssociation> get(Long id) {
        return crudExecutor.get(id, dataFetcher, mapper);
    }

    public FeatureFlagUserAssociation create(FeatureFlagUserAssociation obj) {
        return crudExecutor.create(obj, dataFetcher, mapper);
    }

    public FeatureFlagUserAssociation update(FeatureFlagUserAssociation obj) {
        return crudExecutor.update(obj, dataFetcher, mapper);
    }

    public FeatureFlagUserAssociation merge(FeatureFlagUserAssociation obj) {
        return crudExecutor.merge(obj, dataFetcher, mapper);
    }

    public FeatureFlagUserAssociation remove(Long id) {
        return crudExecutor.delete(id, dataFetcher, mapper);
    }
}
