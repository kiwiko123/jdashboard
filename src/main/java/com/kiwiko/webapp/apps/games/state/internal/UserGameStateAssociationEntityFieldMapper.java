package com.kiwiko.webapp.apps.games.state.internal;

import com.kiwiko.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.webapp.apps.games.state.data.UserGameStateAssociation;
import com.kiwiko.webapp.apps.games.state.internal.dataAccess.UserGameStateAssociationEntity;
import com.kiwiko.library.lang.reflection.properties.api.FieldMapper;
import com.kiwiko.library.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.webapp.users.api.UserService;

import javax.inject.Inject;

public class UserGameStateAssociationEntityFieldMapper extends FieldMapper<UserGameStateAssociationEntity, UserGameStateAssociation> {

    @Inject
    private UserService userService;

    @Inject
    private GameStateService gameStateService;

    @Override
    protected Class<UserGameStateAssociation> getTargetType() {
        return UserGameStateAssociation.class;
    }

    @Override
    public void copyToTarget(UserGameStateAssociationEntity source, UserGameStateAssociation destination) throws PropertyMappingException {
        super.copyToTarget(source, destination);

        userService.getById(source.getUserId())
                .ifPresent(destination::setUser);

        gameStateService.getById(source.getGameStateId())
                .ifPresent(destination::setGameState);
    }
}
