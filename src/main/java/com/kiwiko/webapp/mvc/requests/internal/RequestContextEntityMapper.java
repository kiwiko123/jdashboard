package com.kiwiko.webapp.mvc.requests.internal;

import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.data.User;

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
