package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.dataaccess.impl.CustomJpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;

import javax.inject.Inject;

public class ServiceRequestKeyEntityDataAccessObject extends CustomJpaDataAccessObject<ServiceRequestKeyEntity> {

    @Inject
    public ServiceRequestKeyEntityDataAccessObject(
            FrameworkInternalEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        super(entityManagerProvider, dataChangeCapturer, logger);
    }
}
