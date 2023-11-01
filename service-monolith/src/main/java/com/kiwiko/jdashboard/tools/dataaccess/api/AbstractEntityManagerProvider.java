package com.kiwiko.jdashboard.tools.dataaccess.api;

import com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.EntityManagerProvider;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * An entity manager provider is necessary because an {@link EntityManager} wired through
 * {@link javax.persistence.PersistenceContext} is not available at the time of dependency injection resolution.
 *
 * {@link com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject} implementations can inject a concrete
 * {@link EntityManagerProvider} implementation and access the entity manager via {@link EntityManagerProvider#get()}.
 */
public abstract class AbstractEntityManagerProvider implements EntityManagerProvider {

    private EntityManager entityManager;

    @Nonnull
    @Override
    public EntityManager get() {
        if (entityManager == null) {
            entityManager = getEntityManager();
            Objects.requireNonNull(entityManager, String.format("Entity manager for %s is null", getClass().getName()));
        }
        return entityManager;
    }

    /**
     * @return an entity manager wired through {@link javax.persistence.PersistenceContext}
     */
    @Nonnull
    protected abstract EntityManager getEntityManager();
}
