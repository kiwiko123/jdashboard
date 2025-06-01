package com.kiwiko.jdashboard.servicerequestkeys.service.internal;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.data.ServiceRequestKeyEntity;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.UuidGenerator;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceRequestKeyServiceImpl implements ServiceRequestKeyService {

    @Inject private ServiceRequestKeyEntityDataAccessObject dataAccessObject;
    @Inject private ServiceRequestKeyEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private UuidGenerator uuidGenerator;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public Optional<ServiceRequestKey> get(long id) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.DEFAULT)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .get(id);
    }

    @Override
    public Optional<ServiceRequestKey> getByToken(String token) {
        return transactionProvider.readOnly(() -> dataAccessObject.getByToken(token).map(mapper::toDto));
    }

    @Override
    public List<ServiceRequestKey> getForUsers(Collection<Long> userIds) {
        return transactionProvider.readOnly(
                () -> dataAccessObject.getForUsers(userIds).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()));
    }


    @Override
    public ServiceRequestKey create(ServiceRequestKey serviceRequestKey) {
        String requestToken = uuidGenerator.generateCustomFromTimestamp();
        ServiceRequestKeyEntity entityToCreate = mapper.toEntity(serviceRequestKey);
        entityToCreate.setRequestToken(requestToken);
        entityToCreate.setCreatedDate(Instant.now());
        // TODO set user ID if available

        return transactionProvider.readWrite(() -> {
            ServiceRequestKeyEntity createdEntity = dataAccessObject.save(entityToCreate);
            return mapper.toDto(createdEntity);
        });
    }

    @Override
    public ServiceRequestKey update(ServiceRequestKey serviceRequestKey) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.DEFAULT)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .update(serviceRequestKey);
    }

    @Override
    public ServiceRequestKey merge(ServiceRequestKey serviceRequestKey) {
        return crudExecutor.<ServiceRequestKeyEntity, ServiceRequestKey, ServiceRequestKeyEntityDataAccessObject, ServiceRequestKeyEntityMapper>data()
                .dataSource(JdashboardDataSources.DEFAULT)
                .dataAccessObject(dataAccessObject)
                .mapper(mapper)
                .operation()
                .merge(serviceRequestKey);
    }
}
