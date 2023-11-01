package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLog;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLogService;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data.IncomingApplicationRequestLogDataAccessObject;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data.IncomingApplicationRequestLogEntity;
import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;

public class IncomingApplicationRequestLogServiceImpl implements IncomingApplicationRequestLogService {

    @Inject private IncomingApplicationRequestLogDataAccessObject dataAccessObject;
    @Inject private IncomingApplicationRequestLogEntityMapper entityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public IncomingApplicationRequestLog create(IncomingApplicationRequestLog obj) {
        return crudExecutor.<IncomingApplicationRequestLogEntity, IncomingApplicationRequestLog, IncomingApplicationRequestLogDataAccessObject, IncomingApplicationRequestLogEntityMapper>data()
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .dataAccessObject(dataAccessObject)
                .mapper(entityMapper)
                .operation()
                .create(obj);
    }
}
