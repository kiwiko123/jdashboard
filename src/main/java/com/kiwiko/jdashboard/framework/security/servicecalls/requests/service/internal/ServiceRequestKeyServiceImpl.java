package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data.ServiceRequestKeyEntity;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class ServiceRequestKeyServiceImpl implements ServiceRequestKeyService {

    @Inject private ServiceRequestKeyEntityDataAccessObject dataAccessObject;
    @Inject private ServiceRequestKeyEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public Optional<ServiceRequestKey> get(long id) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .get(id);
    }

    @Override
    public ServiceRequestKey create(ServiceRequestKey serviceRequestKey) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .create(serviceRequestKey);
    }

    @Override
    public ServiceRequestKey update(ServiceRequestKey serviceRequestKey) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .update(serviceRequestKey);
    }

    @Override
    public ServiceRequestKey merge(ServiceRequestKey serviceRequestKey) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .merge(serviceRequestKey);
    }
}
