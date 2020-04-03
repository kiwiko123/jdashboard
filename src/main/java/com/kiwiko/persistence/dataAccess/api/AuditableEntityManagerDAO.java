package com.kiwiko.persistence.dataAccess.api;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;

@Repository
public abstract class AuditableEntityManagerDAO<T extends AuditableEntity> extends EntityManagerDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(T entity) {
        Instant now = Instant.now();
        if (entity.getCreatedDate() == null) {
            entity.setCreatedDate(now);
        }
        entity.setLastUpdatedDate(now);
        entityManager.persist(entity);
    }
}
