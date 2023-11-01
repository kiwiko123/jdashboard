package com.kiwiko.jdashboard.tools.dataaccess.impl;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;
import com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject;
import com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Simple data access object implementation with the ability to read, create, update, and delete records.
 *
 * @param <T> the data entity type
 */
public abstract class SimpleJpaDataAccessObject<T extends LongDataEntity> implements DataAccessObject<T> {
    // Dependencies
    protected final EntityManagerProvider entityManagerProvider;

    // Stateful data
    protected final Class<T> entityType; // Protected so that derived classes can access this without re-calculating the type on each invocation.

    public SimpleJpaDataAccessObject(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
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
        return entityManagerProvider.get().merge(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof SoftDeletable softDeletableDataEntity) {
            softDeletableDataEntity.setIsRemoved(true);
            save(entity);
            return;
        }

        // Warning -- this will hard-delete the record from the database.
        entityManagerProvider.get().remove(entity);
    }

    /**
     * @see EntityManager#flush()
     */
    @Override
    public void flush() {
        entityManagerProvider.get().flush();
    }

    /**
     * @param id the primary key of the record to look up
     * @return the matching record, if any
     */
    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(entityManagerProvider.get().find(entityType, id));
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
            proxy = entityManagerProvider.get().getReference(entityType, id);
        } catch (EntityNotFoundException e) {
            // do nothing
        }
        return Optional.ofNullable(proxy);
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
}
