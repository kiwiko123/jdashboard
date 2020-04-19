package com.kiwiko.persistence.dataAccess.api;

import com.kiwiko.persistence.identification.Identifiable;
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
public abstract class EntityManagerDAO<T extends Identifiable<Long>> {

    @PersistenceContext
    private EntityManager entityManager;

    protected final Class<T> entityType;

    public EntityManagerDAO() {
        entityType = getEntityType();
    }

    protected abstract Class<T> getEntityType();

    /**
     * Similar idea as {@link #getById(long)}, but returns a proxy -- an object whose fields may be lazily fetched.
     * This does not _guarantee_ that all fields will be lazily fetched.
     *
     * @param id the record's ID
     * @return a proxy for the fetched entity
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

    public Optional<T> getById(long id) {
        T entity = entityManager.find(entityType, id);
        return Optional.ofNullable(entity);
    }

    /**
     * Performs a bulk fetch for all the entities with the given IDs.
     * Prefer this over {@link #getById(long)} when multiple entities of the same type need to be fetched.
     *
     * @param ids the ids to fetch
     * @return all entities matching the given IDs
     */
    public Collection<T> getByIds(Collection<Long> ids) {
        CriteriaQuery<T> query = selectWhereIn("id", ids);
        return createQuery(query).getResultList();
    }

    /**
     * Persists the given entity in the database.
     * The entity can be managed or unmanaged.
     *
     * @param entity the entity to persist
     * @return the entity that was saved, which can possibly be managed
     */
    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        T managedEntity = getProxyById(entity.getId())
                .orElseThrow(() -> new PersistenceException(
                        String.format("Failed to find entity of type %s with ID %d", entity.getClass().getName(), entity.getId())));
        entityManager.remove(managedEntity);
    }

    public void flush() {
        entityManager.flush();
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
