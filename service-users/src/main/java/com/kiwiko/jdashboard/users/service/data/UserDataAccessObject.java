package com.kiwiko.jdashboard.users.service.data;

import com.kiwiko.jdashboard.framework.dataaccess.AbstractJpaDataAccessObject;
import com.kiwiko.jdashboard.users.service.data.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.Optional;

public class UserDataAccessObject extends AbstractJpaDataAccessObject<Long, UserEntity> {
    public Optional<UserEntity> getByUsername(String username) {
        CriteriaQuery<UserEntity> criteriaQuery = selectWhereEqual("username", username);
        UserEntity result = createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(result);
    }
}
