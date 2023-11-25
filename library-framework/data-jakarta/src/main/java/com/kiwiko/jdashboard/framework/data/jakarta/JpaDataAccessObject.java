package com.kiwiko.jdashboard.framework.data.jakarta;

import com.kiwiko.jdashboard.framework.data.DataAccessObject;
import com.kiwiko.jdashboard.framework.data.LongDataEntity;
import com.kiwiko.jdashboard.framework.data.SoftDeletable;
import com.kiwiko.jdashboard.langutils.reflection.ReflectionHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JpaDataAccessObject<T extends LongDataEntity> implements DataAccessObject<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaDataAccessObject.class);

    // Spring-provisioned fields.
    @PersistenceContext
    private EntityManager entityManager;

    // Stateful data.
    protected final Class<T> entityType; // Protected so that derived classes can access this without re-calculating the type on each invocation.

    public JpaDataAccessObject() {
        entityType = getEntityType();
    }

    /**
     * Persists the given entity object into the database.
     * A read-write database transaction must be open (e.g., via {@link org.springframework.transaction.annotation.Transactional}).
     *
     * @param entity the entity to persist
     * @return the entity that was saved, which can possibly be managed (live database connection)
     */
    @Override
    public T save(T entity) {
        return persistToDataStore(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof SoftDeletable) {
            SoftDeletable softDeletableDataEntity = (SoftDeletable) entity;
            softDeletableDataEntity.setIsRemoved(true);
            save(entity);
            return;
        }

        // Warning -- this will hard-delete the record from the database.
        entityManager.remove(entity);
    }

    /**
     * @see EntityManager#flush()
     */
    @Override
    public void flush() {
        entityManager.flush();
    }

    /**
     * @param id the primary key of the record to look up
     * @return the matching record, if any
     */
    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    /**
     * Look up a proxy object whose state may be lazily fetched, by possibly avoiding a fetch from the database.
     * If a database fetch was not initially made, it will be made as soon as any of the returned object's fields are accessed.
     *
     * @param id the record's ID
     * @return a proxy entity
     * @see EntityManager#getReference(Class, Object)
     */
    public Optional<T> getProxyById(long id) {
        T proxy = null;
        try {
            proxy = entityManager.getReference(entityType, id);
        } catch (EntityNotFoundException e) {
            // do nothing
        }
        return Optional.ofNullable(proxy);
    }

    /**
     * @see EntityManager#getCriteriaBuilder()
     */
    protected CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    /**
     * @see EntityManager#createQuery(CriteriaQuery)
     */
    protected <U> TypedQuery<U> createQuery(CriteriaQuery<U> query) {
        return entityManager.createQuery(query);
    }

    /**
     * @see EntityManager#createQuery(String, Class)
     */
    protected <U> TypedQuery<U> createQuery(String queryString, Class<U> type) {
        return entityManager.createQuery(queryString, type);
    }

    /**
     * @see #createQuery(String, Class)
     */
    protected TypedQuery<T> createQuery(String queryString) {
        return createQuery(queryString, entityType);
    }

    /**
     * @see EntityManager#createNativeQuery(String)
     */
    protected Query createNativeQuery(String query) {
        return entityManager.createNativeQuery(query, entityType);
    }

    protected <U> Optional<U> getSingleResult(CriteriaQuery<U> query) {
        U result = null;
        try {
            result = createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return Optional.ofNullable(result);
    }

    /**
     * Return the entity's {@link Class} type.
     * By default, this is automatically inferred through reflection.
     * However, it can be overridden (e.g., {@code return MyEntity.class}) if performance is a concern.
     *
     * This is invoked once in the constructor, and can be directly accessed by derived classes via {@link #entityType}.
     *
     * @return the entity's class
     * @see #entityType
     */
    protected Class<T> getEntityType() {
        ReflectionHelper reflectionHelper = new ReflectionHelper();
        return reflectionHelper.getGenericClassType(getClass());
    }

    private T persistToDataStore(T entity) {
        return entityManager.merge(entity);
    }
}
