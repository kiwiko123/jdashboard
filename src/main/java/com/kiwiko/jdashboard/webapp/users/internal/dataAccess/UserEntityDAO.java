package com.kiwiko.jdashboard.webapp.users.internal.dataAccess;

import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUserQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersBulkQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class UserEntityDAO extends EntityDataFetcher<UserEntity> {

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    public Optional<UserEntity> getByUsername(String username) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);
        Expression<String> usernameField = root.get("username");
        Expression<String> lowerCaseUsernameField = builder.lower(usernameField);
        Predicate equalsUsername = builder.equal(lowerCaseUsernameField, username.toLowerCase());

        query.where(equalsUsername);
        return getSingleResult(query);
    }

    public Optional<UserEntity> getByEmailAddress(String emailAddress) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);
        Expression<String> emailAddressField = root.get("emailAddress");
        Expression<String> lowerCaseEmailAddressField = builder.lower(emailAddressField);
        Predicate equalsEmailAddress = builder.equal(lowerCaseEmailAddressField, emailAddress.toLowerCase());

        query.where(equalsEmailAddress);
        return getSingleResult(query);
    }

    public List<UserEntity> getByQuery(GetUsersQuery queryParameters) {
        Set<Long> ids = queryParameters.getQueries().stream()
                .map(GetUserQuery::getId)
                .collect(Collectors.toSet());

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);
        Expression<Long> idField = root.get("id");
        Predicate inIds = idField.in(ids);

        query.where(inIds);
        return createQuery(query).getResultList();
    }

    public Set<UserEntity> getByQuery(GetUsersBulkQuery queryParameters) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);

        List<Predicate> predicates = new LinkedList<>();

        if (queryParameters.getUserIds() != null) {
            Expression<Long> idField = root.get("id");
            predicates.add(idField.in(queryParameters.getUserIds()));
        }

        if (queryParameters.getUsernames() != null) {
            Expression<String> usernameField = root.get("username");
            predicates.add(usernameField.in(queryParameters.getUsernames()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return createQuery(query).getResultStream().collect(Collectors.toSet());
    }
}
