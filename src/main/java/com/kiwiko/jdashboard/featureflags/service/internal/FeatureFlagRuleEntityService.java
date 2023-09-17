package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagRuleService;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagRuleEntityDataAccessObject;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FeatureFlagRuleEntityService implements FeatureFlagRuleService {
    @Inject private FeatureFlagRuleEntityDataAccessObject dataAccessObject;
    @Inject private FeatureFlagRuleEntityMapper featureFlagRuleEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public Optional<FeatureFlagRule> read(long id) {
        return get(id);
    }

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

    public void delete(long id) {
        crudExecutor.delete(id, dataAccessObject, featureFlagRuleEntityMapper);
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

    public Set<FeatureFlagRule> findByFeatureFlagIds(Collection<Long> featureFlagIds) {
        return transactionProvider.readOnly(
                () ->
                        dataAccessObject.findByFeatureFlagIds(featureFlagIds).stream()
                                .map(featureFlagRuleEntityMapper::toDto)
                                .collect(Collectors.toSet()));
    }

    @Override
    public Set<String> getEnabledScopesForRules(Collection<FeatureFlagRule> rules) {
        return rules.stream()
                .filter(rule -> FeatureFlagStatus.ENABLED.equals(rule.getFlagStatus()))
                .map(FeatureFlagRule::getScope)
                .collect(Collectors.toUnmodifiableSet());
    }
}
