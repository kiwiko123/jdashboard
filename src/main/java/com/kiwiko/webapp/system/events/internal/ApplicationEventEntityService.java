package com.kiwiko.webapp.system.events.internal;

import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.webapp.system.events.api.interfaces.ApplicationEventService;
import com.kiwiko.webapp.system.events.api.interfaces.parameters.ApplicationEventQuery;
import com.kiwiko.webapp.system.events.api.dto.ApplicationEvent;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntity;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.webapp.system.events.internal.mappers.ApplicationEventEntityMapper;

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
    public ApplicationEvent create(ApplicationEvent event) {
        ApplicationEventEntity entityToCreate = applicationEventEntityMapper.toEntity(event);
        entityToCreate.setCreatedDate(Instant.now());

        return transactionProvider.readWrite(() -> {
            ApplicationEventEntity createdEntity = applicationEventEntityDataFetcher.save(entityToCreate);
            return applicationEventEntityMapper.toDto(createdEntity);
        });
    }
}
