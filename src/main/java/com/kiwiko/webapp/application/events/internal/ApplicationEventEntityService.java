package com.kiwiko.webapp.application.events.internal;

import com.kiwiko.webapp.application.events.internal.data.ApplicationEventEntity;
import com.kiwiko.webapp.application.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.webapp.application.events.internal.mappers.ApplicationEventEntityMapper;
import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.webapp.application.events.api.interfaces.parameters.ApplicationEventQuery;
import com.kiwiko.webapp.application.events.api.dto.ApplicationEvent;

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

        return transactionProvider.readWrite(() -> {
            ApplicationEventEntity createdEntity = applicationEventEntityDataFetcher.save(entityToCreate);
            return applicationEventEntityMapper.toDto(createdEntity);
        });
    }

    @Override
    public ApplicationEvent update(ApplicationEvent event) {
        return crudExecutor.update(event, applicationEventEntityDataFetcher, applicationEventEntityMapper);
    }

    @Override
    public ApplicationEvent merge(ApplicationEvent event) {
        return crudExecutor.merge(event, applicationEventEntityDataFetcher, applicationEventEntityMapper);
    }
}
