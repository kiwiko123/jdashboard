package com.kiwiko.webapp.system.events.internal.mappers;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.system.events.api.dto.ApplicationEvent;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntity;

public class ApplicationEventEntityMapper extends EntityMapper<ApplicationEventEntity, ApplicationEvent> {

    @Override
    protected Class<ApplicationEventEntity> getEntityType() {
        return ApplicationEventEntity.class;
    }

    @Override
    protected Class<ApplicationEvent> getDTOType() {
        return ApplicationEvent.class;
    }
}
