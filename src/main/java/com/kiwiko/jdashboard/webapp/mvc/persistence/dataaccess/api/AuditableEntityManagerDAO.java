package com.kiwiko.jdashboard.webapp.mvc.persistence.dataaccess.api;

import com.kiwiko.library.persistence.dataAccess.api.AuditableEntity;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public abstract class AuditableEntityManagerDAO<T extends AuditableEntity> extends EntityManagerDAO<T> {

    @Override
    public T save(T entity) {
        Instant now = Instant.now();
        if (entity.getCreatedDate() == null) {
            entity.setCreatedDate(now);
        }
        entity.setLastUpdatedDate(now);
        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        entity.setIsRemoved(true);
        save(entity);
    }
}
