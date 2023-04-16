package com.kiwiko.jdashboard.servicerequestkeys.service.internal.data;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.dataaccess.impl.CustomJpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class ServiceRequestKeyEntityDataAccessObject extends CustomJpaDataAccessObject<ServiceRequestKeyEntity> {

    @Inject
    public ServiceRequestKeyEntityDataAccessObject(
            FrameworkInternalEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        super(entityManagerProvider, dataChangeCapturer, logger);
    }

    public Optional<ServiceRequestKeyEntity> getByToken(String token) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<ServiceRequestKeyEntity> criteriaQuery = criteriaBuilder.createQuery(entityType);
        Root<ServiceRequestKeyEntity> root = criteriaQuery.from(entityType);

        Expression<String> requestTokenField = root.get("requestToken");
        Predicate matchesToken = criteriaBuilder.equal(requestTokenField, token);

        criteriaQuery.where(matchesToken);
        return getSingleResult(criteriaQuery);
    }
}
