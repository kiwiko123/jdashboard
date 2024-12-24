package com.kiwiko.jdashboard.tools.dataaccess.impl;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.EntityManagerProvider;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;

/**
 * Data access object with JPA criteria entrypoint methods.
 *
 * @param <T> the data entity type
 */
@Repository
public abstract class AbstractJpaDataAccessObject<T extends LongDataEntity> extends SimpleJpaDataAccessObject<T> {

    public AbstractJpaDataAccessObject(EntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider);
    }

    /**
     * @see EntityManager#getCriteriaBuilder()
     */
    protected CriteriaBuilder getCriteriaBuilder() {
        return entityManagerProvider.get().getCriteriaBuilder();
    }

    /**
     * @see EntityManager#createQuery(CriteriaQuery)
     */
    protected <U> TypedQuery<U> createQuery(CriteriaQuery<U> query) {
        return entityManagerProvider.get().createQuery(query);
    }

    /**
     * @see EntityManager#createQuery(String, Class)
     */
    protected <U> TypedQuery<U> createQuery(String queryString, Class<U> type) {
        return entityManagerProvider.get().createQuery(queryString, type);
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
        return entityManagerProvider.get().createNativeQuery(query, entityType);
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
}
