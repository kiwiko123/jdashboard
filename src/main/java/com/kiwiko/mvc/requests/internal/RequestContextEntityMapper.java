package com.kiwiko.mvc.requests.internal;

import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.persistence.properties.api.EntityMapper;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RequestContextEntityMapper extends EntityMapper<RequestContextEntity, RequestContext> {

    @Inject
    private UserService userService;

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }

    @Override
    protected Class<RequestContext> getDTOType() {
        return RequestContext.class;
    }

    @Override
    public void copyToEntity(RequestContext dto, RequestContextEntity entity) {
        super.copyToEntity(dto, entity);
        dto.getUser()
                .map(User::getId)
                .ifPresent(entity::setUserId);
    }

    @Override
    public void copyToDTO(RequestContextEntity entity, RequestContext dto) {
        super.copyToDTO(entity, dto);
        Optional.ofNullable(entity.getUserId())
                .flatMap(userService::getById)
                .ifPresent(dto::setUser);
    }
}
