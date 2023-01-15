package com.kiwiko.jdashboard.webapp.application.events.internal;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntity;
import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.application.events.internal.mappers.ApplicationEventEntityMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.application.events.internal.streaming.EmitApplicationEventOperations;
import com.kiwiko.jdashboard.webapp.application.events.internal.streaming.EmitApplicationEventRequest;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.parameters.ApplicationEventQuery;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationEventEntityService implements ApplicationEventService {

    @Inject private ApplicationEventEntityDataFetcher applicationEventEntityDataFetcher;
    @Inject private ApplicationEventEntityMapper applicationEventEntityMapper;
    @Inject private TransactionProvider transactionProvider;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public Optional<ApplicationEvent> get(long id) {
        return crudExecutor.<ApplicationEventEntity, ApplicationEvent, ApplicationEventEntityDataFetcher, ApplicationEventEntityMapper>data()
                .dataAccessObject(applicationEventEntityDataFetcher)
                .mapper(applicationEventEntityMapper)
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .operation()
                .get(id);
    }

    @Override
    public Set<ApplicationEvent> query(ApplicationEventQuery query) {
        Objects.requireNonNull(query.getEventType(), "Event type is required");
        return transactionProvider.readOnly(
                JdashboardDataSources.FRAMEWORK_INTERNAL,
                () -> applicationEventEntityDataFetcher.getByQuery(query).stream()
                    .map(applicationEventEntityMapper::toDto)
                    .collect(Collectors.toSet()));
    }

    @Override
    public Set<ApplicationEvent> queryLike(ApplicationEventQuery query) {
        Objects.requireNonNull(query.getEventType(), "Event type is required");
        return transactionProvider.readOnly(
                JdashboardDataSources.FRAMEWORK_INTERNAL,
                () -> applicationEventEntityDataFetcher.getByQueryLike(query).stream()
                    .map(applicationEventEntityMapper::toDto)
                    .collect(Collectors.toSet()));
    }

    @Override
    public ApplicationEvent create(ApplicationEvent event) {
        ApplicationEventEntity entityToCreate = applicationEventEntityMapper.toEntity(event);
        entityToCreate.setCreatedDate(Instant.now());

        ApplicationEvent createdEvent = transactionProvider.readWrite(
                JdashboardDataSources.FRAMEWORK_INTERNAL,
                () -> {
                    ApplicationEventEntity createdEntity = applicationEventEntityDataFetcher.save(entityToCreate);
                    return applicationEventEntityMapper.toDto(createdEntity);
                });

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(createdEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.CREATED);

        return createdEvent;
    }

    @Override
    public ApplicationEvent update(ApplicationEvent event) {
        ApplicationEvent updatedEvent = crudExecutor.<ApplicationEventEntity, ApplicationEvent, ApplicationEventEntityDataFetcher, ApplicationEventEntityMapper>data()
                .dataAccessObject(applicationEventEntityDataFetcher)
                .mapper(applicationEventEntityMapper)
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .operation()
                .update(event);

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(updatedEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.UPDATED);

        return updatedEvent;
    }

    @Override
    public ApplicationEvent merge(ApplicationEvent event) {
        ApplicationEvent updatedEvent = crudExecutor.<ApplicationEventEntity, ApplicationEvent, ApplicationEventEntityDataFetcher, ApplicationEventEntityMapper>data()
                .dataAccessObject(applicationEventEntityDataFetcher)
                .mapper(applicationEventEntityMapper)
                .dataSource(JdashboardDataSources.FRAMEWORK_INTERNAL)
                .operation()
                .merge(event);

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(updatedEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.UPDATED);

        return updatedEvent;
    }
}
