package com.kiwiko.users.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Singleton
public class UserEntityDAO extends AuditableEntityManagerDAO<UserEntity> {

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    public Optional<UserEntity> getByUsername(String username) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);
        Expression<String> usernameField = root.get("username");
        Expression<String> lowerCaseUsernameField = builder.lower(usernameField);
        Predicate equalsUsername = builder.equal(lowerCaseUsernameField, username.toLowerCase());

        query.where(equalsUsername);
        return getSingleResult(query);
    }

    public Optional<UserEntity> getByEmailAddress(String emailAddress) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(entityType);
        Root<UserEntity> root = query.from(entityType);
        Expression<String> emailAddressField = root.get("emailAddress");
        Expression<String> lowerCaseEmailAddressField = builder.lower(emailAddressField);
        Predicate equalsEmailAddress = builder.equal(lowerCaseEmailAddressField, emailAddress.toLowerCase());

        query.where(equalsEmailAddress);
        return getSingleResult(query);
    }
}
