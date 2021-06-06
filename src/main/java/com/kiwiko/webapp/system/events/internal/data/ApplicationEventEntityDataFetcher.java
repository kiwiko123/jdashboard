package com.kiwiko.webapp.system.events.internal.data;

import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;

public class ApplicationEventEntityDataFetcher extends EntityManagerDAO<ApplicationEventEntity> {

    @Override
    protected Class<ApplicationEventEntity> getEntityType() {
        return ApplicationEventEntity.class;
    }
}
