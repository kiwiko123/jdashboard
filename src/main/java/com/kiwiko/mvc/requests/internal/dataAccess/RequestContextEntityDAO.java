package com.kiwiko.mvc.requests.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Singleton
public class RequestContextEntityDAO extends AuditableEntityManagerDAO<RequestContextEntity> {

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }

    public Collection<RequestContextEntity> getByUri(String uri, int maxResults) {
        CriteriaQuery<RequestContextEntity> query = selectWhereEqual("uri", uri);
        return createQuery(query)
                .setMaxResults(maxResults)
                .getResultList();
    }
}
