package com.kiwiko.webapp.persistence.data.fetchers.api.interfaces;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.webapp.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.webapp.persistence.data.api.interfaces.SoftDeletableDataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.CaptureEntityDataChanges;
import com.kiwiko.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
@Singleton
public abstract class EntityDataFetcher<T extends DataEntity> {

    @PersistenceContext private EntityManager entityManager;
    private final ReflectionHelper reflectionHelper;
    @Inject private DataChangeCapturer dataChangeCapturer;

    protected final Class<T> entityType;
    private final boolean captureDataChanges;

    public EntityDataFetcher() {
        reflectionHelper = new ReflectionHelper();
        entityType = getEntityType();
        captureDataChanges = entityType.getAnnotation(CaptureEntityDataChanges.class) != null;
    }

    /**
     * @return the entity's class
     */
    protected Class<T> getEntityType() {
        return reflectionHelper.getGenericClassType(getClass());
    }

    /**
     * Persists the given entity object into the database.
     * A read-write database transaction must be open (e.g., via {@link org.springframework.transaction.annotation.Transactional}).
     *
     * @param entity the entity to persist
     * @return the entity that was saved, which can possibly be managed (live database connection)
     */
    public final T save(T entity) {
        if (captureDataChanges) {
            return dataChangeCapturer.save(entity, this::getById, entityManager::merge);
        }

        return entityManager.merge(entity);
    }

    public final void delete(T entity) {
        if (entity instanceof SoftDeletableDataEntity) {
            SoftDeletableDataEntity softDeletableDataEntity = (SoftDeletableDataEntity) entity;
            softDeletableDataEntity.setIsRemoved(true);
            save(entity);
            return;
        }

        // Warning -- this will hard-delete the record from the database.
        entityManager.remove(entity);
    }

    /**
     * @param id the primary key of the record to look up
     * @return the matching record, if any
     */
    public final Optional<T> getById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    /**
     * Look up a proxy object whose state may be lazily fetched, by possibly avoiding a fetch from the database.
     * If a database fetch was not initially made, it will be made as soon as any of the returned object's fields are accessed.
     *
     * @param id the record's ID
     * @return a proxy entity
     */
    public final Optional<T> getProxyById(long id) {
        T proxy = null;
        try {
            proxy = entityManager.getReference(entityType, id);
        } catch (EntityNotFoundException e) {
            // do nothing
        }
        return Optional.ofNullable(proxy);
    }

    /**
     * Performs a single bulk database fetch for all the entities with the given IDs.
     * Prefer this over {@link #getById(long)} when multiple entities of the same type need to be fetched.
     *
     * @param ids the ids to fetch
     * @return all entities matching the given IDs
     */
    public final Collection<T> getByIds(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityType);
        Root<T> root = query.from(entityType);

        Expression<Long> idField = root.get("id");

        Predicate hasId = idField.in(ids);
        query.where(hasId);

        return createQuery(query).getResultList();
    }

    public final void flush() {
        entityManager.flush();
    }

    protected final CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    protected final TypedQuery<T> createQuery(CriteriaQuery<T> query) {
        return entityManager.createQuery(query);
    }

    protected Optional<T> getSingleResult(CriteriaQuery<T> query) {
        T result = null;
        try {
            result = createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return Optional.ofNullable(result);
    }
}
