package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.dataaccess.impl.CustomJpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

public class IncomingApplicationRequestLogDataAccessObject extends CustomJpaDataAccessObject<IncomingApplicationRequestLogEntity> {

    @Inject
    public IncomingApplicationRequestLogDataAccessObject(
            FrameworkInternalEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        super(entityManagerProvider, dataChangeCapturer, logger);
    }

    List<IncomingApplicationRequestLogEntity> getMostRecentFromIpAddress(
            String ipAddress,
            String uri,
            Instant lookback) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<IncomingApplicationRequestLogEntity> criteriaQuery = builder.createQuery(IncomingApplicationRequestLogEntity.class);
        Root<IncomingApplicationRequestLogEntity> root = criteriaQuery.from(IncomingApplicationRequestLogEntity.class);

        Predicate fromIpAddress = builder.equal(root.get("ipAddress"), ipAddress);
        Predicate sinceLookback = builder.greaterThanOrEqualTo(root.get("requestDate"), lookback);

        criteriaQuery.where(fromIpAddress, sinceLookback);
        return createQuery(criteriaQuery).getResultList();
    }
}
