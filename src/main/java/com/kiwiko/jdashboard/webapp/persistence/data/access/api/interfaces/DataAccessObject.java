package com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces;

import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.SuccessConfidenceLevel;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.parameters.SaveDataChangeCaptureParameters;
import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.EntityChangeDataCapturer;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public abstract class DataAccessObject<T extends DataEntity> {

    // Spring-provisioned fields.
    @PersistenceContext private EntityManager entityManager;
    @Inject private EntityChangeDataCapturer dataChangeCapturer;
    @Inject private Logger logger;

    // Stateful data.
    private final @Nullable CaptureDataChanges captureDataChanges;
    protected final Class<T> entityType; // Protected so that derived classes can access this without re-calculating the type on each invocation.

    public DataAccessObject() {
        entityType = getEntityType();
        captureDataChanges = entityType.getAnnotation(CaptureDataChanges.class);
    }

    /**
     * Persists the given entity object into the database.
     * A read-write database transaction must be open (e.g., via {@link org.springframework.transaction.annotation.Transactional}).
     *
     * @param entity the entity to persist
     * @return the entity that was saved, which can possibly be managed (live database connection)
     */
    public T save(T entity) {
        if (captureDataChanges != null) {
            return changeDataCaptureSave(entity);
        }

        return persistToDataStore(entity);
    }

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
    public void flush() {
        entityManager.flush();
    }

    /**
     * @param id the primary key of the record to look up
     * @return the matching record, if any
     */
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
     * Performs a single bulk database fetch for all the entities with the given IDs.
     * Prefer this over {@link #getById(long)} when multiple entities of the same type need to be fetched.
     *
     * @param ids the ids to fetch
     * @return all entities matching the given IDs
     */
    public List<T> getByIds(Collection<Long> ids) {
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
     * @see EntityManager#createNativeQuery(String, Class) 
     */
    protected <U> Query createNativeQuery(String query, Class<U> type) {
        return entityManager.createNativeQuery(query, type);
    }

    protected <U> Query createNativeQuery(String query) {
        return createNativeQuery(query, entityType);
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

    private T changeDataCaptureSave(T entity) {
        Objects.requireNonNull(captureDataChanges, "@CaptureDataChanges is required");

        SaveDataChangeCaptureParameters<T> parameters = new SaveDataChangeCaptureParameters<>();
        parameters.setCaptureDataChanges(captureDataChanges);
        parameters.setEntity(entity);
        parameters.setGetEntityById(this::getById);
        parameters.setSaveEntity(this::persistToDataStore);

        try {
            return dataChangeCapturer.save(parameters);
        } catch (Exception e) {
            logger.error(
                    "Error capturing data change for entity {} at confidence level {}",
                    entity,
                    captureDataChanges.successConfidence(),
                    e);
            if (captureDataChanges.successConfidence() == SuccessConfidenceLevel.CONFIDENT) {
                throw e;
            }
        }

        return persistToDataStore(entity);
    }
}
