package com.kiwiko.webapp.system.events.internal;

import com.kiwiko.webapp.system.events.api.ApplicationEventService;
import com.kiwiko.webapp.system.events.dto.ApplicationEvent;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntity;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.webapp.system.events.internal.mappers.ApplicationEventEntityMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

public class ApplicationEventEntityService implements ApplicationEventService {

    @Inject private ApplicationEventEntityDataFetcher applicationEventEntityDataFetcher;
    @Inject private ApplicationEventEntityMapper applicationEventEntityMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicationEvent> get(long id) {
        return applicationEventEntityDataFetcher.getById(id)
                .map(applicationEventEntityMapper::toDTO);
    }

    @Transactional
    @Override
    public ApplicationEvent create(ApplicationEvent event) {
        ApplicationEventEntity entityToCreate = applicationEventEntityMapper.toEntity(event);
        entityToCreate.setCreatedDate(Instant.now());

        ApplicationEventEntity createdEntity = applicationEventEntityDataFetcher.save(entityToCreate);
        return applicationEventEntityMapper.toDTO(createdEntity);
    }
}
