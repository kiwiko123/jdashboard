package com.kiwiko.webapp.games.state.internal;

import com.kiwiko.webapp.games.state.data.UserGameStateAssociation;

import java.util.Collection;
import java.util.Optional;

public interface UserGameStateAssociationService {

    Collection<UserGameStateAssociation> findByUser(long userId);

    Optional<UserGameStateAssociation> findByGameStateAndUser(long gameStateId, long userId);

    UserGameStateAssociation create(long gameStateId, long userId);
}
