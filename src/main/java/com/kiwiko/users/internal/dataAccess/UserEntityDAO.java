package com.kiwiko.users.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;

@Singleton
public class UserEntityDAO extends AuditableEntityManagerDAO<UserEntity> {

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    public Optional<UserEntity> getByEmailAddress(String emailAddress) {
        CriteriaQuery<UserEntity> query = selectWhereEqual("emailAddress", emailAddress);
        return getSingleResult(query);
    }
}
