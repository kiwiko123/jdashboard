package com.kiwiko.jdashboard.webapp.application.events.internal;

import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntity;
import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.application.events.internal.mappers.ApplicationEventEntityMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.application.events.internal.streaming.ApplicationEventEmitter;
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
    @Inject private ApplicationEventEmitter applicationEventEmitter;
    @Inject private TransactionProvider transactionProvider;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public Optional<ApplicationEvent> get(long id) {
        return crudExecutor.get(id, applicationEventEntityDataFetcher, applicationEventEntityMapper);
    }

    @Override
    public Set<ApplicationEvent> query(ApplicationEventQuery query) {
        Objects.requireNonNull(query.getEventType(), "Event type is required");
        return transactionProvider.readOnly(() -> applicationEventEntityDataFetcher.getByQuery(query).stream()
                .map(applicationEventEntityMapper::toDto)
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<ApplicationEvent> queryLike(ApplicationEventQuery query) {
        Objects.requireNonNull(query.getEventType(), "Event type is required");
        return transactionProvider.readOnly(() -> applicationEventEntityDataFetcher.getByQueryLike(query).stream()
                .map(applicationEventEntityMapper::toDto)
                .collect(Collectors.toSet()));
    }

    @Override
    public ApplicationEvent create(ApplicationEvent event) {
        ApplicationEventEntity entityToCreate = applicationEventEntityMapper.toEntity(event);
        entityToCreate.setCreatedDate(Instant.now());

        ApplicationEvent createdEvent = transactionProvider.readWrite(applicationEventEntityDataFetcher.getDataSource(), () -> {
            ApplicationEventEntity createdEntity = applicationEventEntityDataFetcher.save(entityToCreate);
            return applicationEventEntityMapper.toDto(createdEntity);
        });

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(createdEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.CREATED);
        applicationEventEmitter.emit(emitApplicationEventRequest);

        return createdEvent;
    }

    @Override
    public ApplicationEvent update(ApplicationEvent event) {
        ApplicationEvent updatedEvent = crudExecutor.update(event, applicationEventEntityDataFetcher, applicationEventEntityMapper);

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(updatedEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.UPDATED);
        applicationEventEmitter.emit(emitApplicationEventRequest);

        return updatedEvent;
    }

    @Override
    public ApplicationEvent merge(ApplicationEvent event) {
        ApplicationEvent updatedEvent = crudExecutor.merge(event, applicationEventEntityDataFetcher, applicationEventEntityMapper);

        EmitApplicationEventRequest emitApplicationEventRequest = new EmitApplicationEventRequest();
        emitApplicationEventRequest.setEvent(updatedEvent);
        emitApplicationEventRequest.setOperation(EmitApplicationEventOperations.UPDATED);
        applicationEventEmitter.emit(emitApplicationEventRequest);

        return updatedEvent;
    }
}
