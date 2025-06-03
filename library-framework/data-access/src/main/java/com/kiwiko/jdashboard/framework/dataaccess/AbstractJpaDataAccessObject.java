package com.kiwiko.jdashboard.framework.dataaccess;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractJpaDataAccessObject<Id, Entity extends DataEntity<Id>> {
    @PersistenceContext private EntityManager entityManager;
    protected final Class<Entity> entityType;

    public AbstractJpaDataAccessObject() {
        this.entityType = getEntityType();
    }

    @Nonnull
    public Optional<Entity> get(@Nonnull Id id) {
        Entity result = entityManager.find(entityType, id);
        return Optional.ofNullable(result);
    }

    @Nonnull
    public Optional<Entity> getReference(@Nonnull Id id) {
        Entity reference = null;

        try {
            reference = entityManager.getReference(entityType, id);
        } catch (EntityNotFoundException e) {
            // Do nothing
        }

        return Optional.ofNullable(reference);
    }

    public List<Entity> get(Collection<Id> ids) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<Entity> query = criteriaBuilder.createQuery(entityType);
        Root<Entity> root = query.from(entityType);

        Predicate hasId = root.get("id").in(ids);

        query.select(root).where(hasId);

        return createQuery(query).getResultList();
    }

    @Nonnull
    public Entity insert(@Nonnull Entity entity) {
        return save(entity);
    }

    @Nonnull
    public Entity update(@Nonnull Entity entity) {
        return save(entity);
    }

    public void delete(@Nonnull Entity entity) {
        entityManager.remove(entity);
    }

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
    protected TypedQuery<Entity> createQuery(String queryString) {
        return createQuery(queryString, entityType);
    }

    /**
     * @see EntityManager#createNativeQuery(String)
     */
    protected Query createNativeQuery(String query) {
        return entityManager.createNativeQuery(query, entityType);
    }

    protected CriteriaQuery<Entity> selectWhereEqual(String field, Object expectedValue) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<Entity> query = criteriaBuilder.createQuery(entityType);
        Root<Entity> root = query.from(entityType);

        Predicate predicate = criteriaBuilder.equal(root.get(field), expectedValue);
        return query.select(root).where(predicate);
    }

    protected Class<Entity> getEntityType() {
        @SuppressWarnings("unchecked")
        Class<Entity> type = (Class<Entity>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        return type;
    }

    private Entity save(Entity entity) {
        return entityManager.merge(entity);
    }
}
