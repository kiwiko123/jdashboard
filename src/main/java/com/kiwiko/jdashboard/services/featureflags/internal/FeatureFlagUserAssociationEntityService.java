package com.kiwiko.jdashboard.services.featureflags.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserAssociation;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.services.featureflags.internal.data.FeatureFlagUserAssociationEntity;
import com.kiwiko.jdashboard.services.featureflags.internal.data.FeatureFlagUserAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteService;

import javax.inject.Inject;

public class FeatureFlagUserAssociationEntityService extends CreateReadUpdateDeleteService<FeatureFlagUserAssociationEntity, FeatureFlagUserAssociation, FeatureFlagUserAssociationEntityDataFetcher> {

    @Inject
    public FeatureFlagUserAssociationEntityService(
            FeatureFlagUserAssociationEntityDataFetcher dataAccessObject,
            FeatureFlagUserAssociationEntityMapper mapper,
            TransactionProvider transactionProvider) {
        super(dataAccessObject, mapper, transactionProvider);
    }
}
