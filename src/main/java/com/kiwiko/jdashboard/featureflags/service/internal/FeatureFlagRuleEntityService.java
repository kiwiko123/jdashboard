package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagContext;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagContextEntityDataAccessObject;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class FeatureFlagContextEntityService {
    @Inject private FeatureFlagContextEntityDataAccessObject dataAccessObject;
    @Inject private FeatureFlagContextEntityMapper featureFlagContextEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    public Optional<FeatureFlagContext> get(long id) {
        return crudExecutor.get(id, dataAccessObject, featureFlagContextEntityMapper);
    }

    public FeatureFlagContext create(FeatureFlagContext obj) {
        return crudExecutor.create(obj, dataAccessObject, featureFlagContextEntityMapper);
    }

    public FeatureFlagContext update(FeatureFlagContext obj) {
        return crudExecutor.update(obj, dataAccessObject, featureFlagContextEntityMapper);
    }

    public FeatureFlagContext merge(FeatureFlagContext obj) {
        return crudExecutor.merge(obj, dataAccessObject, featureFlagContextEntityMapper);
    }

    public FeatureFlagContext delete(long id) {
        return crudExecutor.delete(id, dataAccessObject, featureFlagContextEntityMapper);
    }

    public Optional<FeatureFlagContext> findPublic(long featureFlagId) {
        return transactionProvider.readOnly(
                () ->
                        dataAccessObject.findMostRecentActive(
                                featureFlagId,
                                FeatureFlagUserScope.PUBLIC.getId(),
                                null)
                                .map(featureFlagContextEntityMapper::toDto));
    }

    public Optional<FeatureFlagContext> findForUser(long featureFlagId, long userId) {
        return transactionProvider.readOnly(
                () ->
                        dataAccessObject.findMostRecentActive(
                                featureFlagId,
                                FeatureFlagUserScope.INDIVIDUAL.getId(),
                                userId)
                                .map(featureFlagContextEntityMapper::toDto));
    }
}
