package com.kiwiko.mvc.requests.internal;

import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RequestContextEntityPropertyMapper extends BidirectionalFieldMapper<RequestContextEntity, RequestContext> {

    @Inject
    private UserService userService;

    @Override
    protected Class<RequestContextEntity> getSourceType() {
        return RequestContextEntity.class;
    }

    @Override
    protected Class<RequestContext> getTargetType() {
        return RequestContext.class;
    }

    @Override
    public void toSource(RequestContext dto, RequestContextEntity entity) {
        super.toSource(dto, entity);
        dto.getUser()
                .map(User::getId)
                .ifPresent(entity::setUserId);
    }

    @Override
    public void toTarget(RequestContextEntity entity, RequestContext dto) {
        super.toTarget(entity, dto);
        Optional.ofNullable(entity.getUserId())
                .flatMap(userService::getById)
                .ifPresent(dto::setUser);
    }
}
