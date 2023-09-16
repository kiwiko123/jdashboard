package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagRuleEntityDataAccessObject;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class FeatureFlagRuleEntityService {
    @Inject private FeatureFlagRuleEntityDataAccessObject dataAccessObject;
    @Inject private FeatureFlagRuleEntityMapper featureFlagRuleEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    public Optional<FeatureFlagRule> get(long id) {
        return crudExecutor.get(id, dataAccessObject, featureFlagRuleEntityMapper);
    }

    public FeatureFlagRule create(FeatureFlagRule obj) {
        return crudExecutor.create(obj, dataAccessObject, featureFlagRuleEntityMapper);
    }

    public FeatureFlagRule update(FeatureFlagRule obj) {
        return crudExecutor.update(obj, dataAccessObject, featureFlagRuleEntityMapper);
    }

    public FeatureFlagRule merge(FeatureFlagRule obj) {
        return crudExecutor.merge(obj, dataAccessObject, featureFlagRuleEntityMapper);
    }

    public FeatureFlagRule delete(long id) {
        return crudExecutor.delete(id, dataAccessObject, featureFlagRuleEntityMapper);
    }

    public Optional<FeatureFlagRule> findPublic(long featureFlagId) {
        return transactionProvider.readOnly(
                () ->
                        dataAccessObject.findMostRecentActive(
                                featureFlagId,
                                FeatureFlagUserScope.PUBLIC.getId(),
                                null)
                                .map(featureFlagRuleEntityMapper::toDto));
    }

    public Optional<FeatureFlagRule> findForUser(long featureFlagId, long userId) {
        return transactionProvider.readOnly(
                () ->
                        dataAccessObject.findMostRecentActive(
                                featureFlagId,
                                FeatureFlagUserScope.INDIVIDUAL.getId(),
                                userId)
                                .map(featureFlagRuleEntityMapper::toDto));
    }
}
