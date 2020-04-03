package com.kiwiko.persistence.dataAccess.api;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public abstract class EntityManagerDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    protected final Class<T> entityType;

    public EntityManagerDAO() {
        entityType = getEntityType();
    }

    protected abstract Class<T> getEntityType();

    public Optional<T> getProxyById(long id) {
        T proxy = null;
        try {
            proxy = entityManager.getReference(entityType, id);
        } catch (EntityNotFoundException e) {
            // do nothing
        }
        return Optional.ofNullable(proxy);
    }

    public Optional<T> getById(long id) {
        T entity = entityManager.find(entityType, id);
        return Optional.ofNullable(entity);
    }

    public Collection<T> getByIds(Collection<Long> ids) {
        CriteriaQuery<T> query = selectWhereIn("id", ids);
        return createQuery(query).getResultList();
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    protected CriteriaBuilder criteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    protected Query createQuery(CriteriaQuery<T> query) {
        return entityManager.createQuery(query);
    }

    protected Optional<T> getSingleResult(CriteriaQuery<T> query) {
        T result = null;
        try {
            result = (T) createQuery(query).getSingleResult();
        } catch (NoResultException e){
            // do nothing
        }

        return Optional.ofNullable(result);
    }

    protected <V> CriteriaQuery<T> selectWhereEqual(String fieldName, V value) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityType);
        Root<T> root = query.from(entityType);
        Expression<V> field = root.get(fieldName);
        Predicate condition = builder.equal(field, value);

        return query.where(condition);
    }

    protected <V> CriteriaQuery<T> selectWhereIn(String fieldName, Collection<V> values) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityType);
        Root<T> root = query.from(entityType);
        Expression<V> field = root.get(fieldName);
        Predicate fieldInValues = field.in(values);

        return query.where(fieldInValues);
    }
}
