package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserAssociation;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagUserAssociationEntity;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagUserAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteService;

import javax.inject.Inject;
import java.util.Optional;

public class FeatureFlagUserAssociationEntityService extends CreateReadUpdateDeleteService<FeatureFlagUserAssociationEntity, FeatureFlagUserAssociation, FeatureFlagUserAssociationEntityDataFetcher> {

    @Inject
    public FeatureFlagUserAssociationEntityService(
            FeatureFlagUserAssociationEntityDataFetcher dataAccessObject,
            FeatureFlagUserAssociationEntityMapper mapper,
            TransactionProvider transactionProvider) {
        super(dataAccessObject, mapper, transactionProvider);
    }

    public Optional<FeatureFlagUserAssociation> getByFeatureFlagIdForUser(long featureFlagId, long userId) {
        return transactionProvider.readOnly(
                () -> dataAccessObject.getByFeatureFlagIdForUser(featureFlagId, userId).map(mapper::toDto));
    }
}
