package com.kiwiko.jdashboard.webapp.apps.games.state.internal;

import com.kiwiko.jdashboard.webapp.apps.games.state.data.UserGameStateAssociation;

import java.util.Optional;
import java.util.Set;

public interface UserGameStateAssociationService {

    Set<UserGameStateAssociation> findByUser(long userId);

    Optional<UserGameStateAssociation> findByGameStateAndUser(long gameStateId, long userId);

    UserGameStateAssociation create(UserGameStateAssociation association);
}
