package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.framework.dataaccess.mapper.AbstractDataEntityMapper;
import com.kiwiko.jdashboard.users.service.data.entity.UserCredentialEntity;
import com.kiwiko.jdashboard.users.service.dto.UserCredential;

public class UserCredentialEntityMapper extends AbstractDataEntityMapper<UserCredentialEntity, UserCredential> {
    @Override
    public UserCredentialEntity toEntity(UserCredential userCredential) {
        UserCredentialEntity entity = new UserCredentialEntity();

        entity.setId(userCredential.getId());
        entity.setUserId(userCredential.getUserId());
        entity.setCredentialType(userCredential.getCredentialType());
        entity.setCredentialValue(userCredential.getCredentialValue());
        entity.setCreatedDate(userCredential.getCreatedDate());
        entity.setIsRemoved(userCredential.isRemoved());

        return entity;
    }

    @Override
    public UserCredential toDto(UserCredentialEntity userCredentialEntity) {
        return UserCredential.builder()
                .id(userCredentialEntity.getId())
                .userId(userCredentialEntity.getUserId())
                .credentialType(userCredentialEntity.getCredentialType())
                .credentialValue(userCredentialEntity.getCredentialValue())
                .createdDate(userCredentialEntity.getCreatedDate())
                .isRemoved(userCredentialEntity.getIsRemoved())
                .build();
    }
}
