package com.kiwiko.webapp.persistence.identification.unique.internal.data;

import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class UniversalUniqueIdentifierEntityDataFetcher extends EntityDataFetcher<UniversalUniqueIdentifierEntity> {

     public Optional<UniversalUniqueIdentifierEntity> getByReferenceKey(String referenceKey) {
         CriteriaBuilder builder = getCriteriaBuilder();
         CriteriaQuery<UniversalUniqueIdentifierEntity> query = builder.createQuery(entityType);
         Root<UniversalUniqueIdentifierEntity> root = query.from(entityType);

         Predicate isReferenceKey = builder.equal(root.get("referenceKey"), referenceKey);
         query.where(isReferenceKey);

         return getSingleResult(query);
     }

     public Optional<UniversalUniqueIdentifierEntity> getByUuid(String uuid) {
         CriteriaBuilder builder = getCriteriaBuilder();
         CriteriaQuery<UniversalUniqueIdentifierEntity> query = builder.createQuery(entityType);
         Root<UniversalUniqueIdentifierEntity> root = query.from(entityType);

         Predicate isUuid = builder.equal(root.get("uuid"), uuid);
         query.where(isUuid);

         return getSingleResult(query);
     }
}
