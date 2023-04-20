package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.dataaccess.impl.CustomJpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;

import javax.inject.Inject;

public class IncomingApplicationRequestLogDataAccessObject extends CustomJpaDataAccessObject<IncomingApplicationRequestLogEntity> {

    @Inject
    public IncomingApplicationRequestLogDataAccessObject(
            FrameworkInternalEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        super(entityManagerProvider, dataChangeCapturer, logger);
    }
}
