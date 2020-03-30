package com.kiwiko.users.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.EntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;

@Singleton
public class UserEntityDAO extends EntityManagerDAO<UserEntity> {

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    public Optional<UserEntity> getByEmailAddress(String emailAddress) {
        CriteriaQuery<UserEntity> query = selectWhereEqual("emailAddress", emailAddress);
        return getSingleResult(query);
    }
}
