package com.kiwiko.mvc.requests.internal;

import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.mvc.requests.data.RequestContextDTO;
import com.kiwiko.mvc.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RequestContextEntityPropertyMapper extends BidirectionalFieldMapper<RequestContextEntity, RequestContextDTO> {

    @Inject
    private UserService userService;

    @Override
    protected Class<RequestContextEntity> getSourceType() {
        return RequestContextEntity.class;
    }

    @Override
    protected Class<RequestContextDTO> getTargetType() {
        return RequestContextDTO.class;
    }

    @Override
    public void toSource(RequestContextDTO dto, RequestContextEntity entity) {
        super.toSource(dto, entity);
        dto.getUser()
                .map(User::getId)
                .ifPresent(entity::setUserId);
    }

    @Override
    public void toTarget(RequestContextEntity entity, RequestContextDTO dto) {
        super.toTarget(entity, dto);
        Optional.ofNullable(entity.getUserId())
                .flatMap(userService::getById)
                .ifPresent(dto::setUser);
    }
}
