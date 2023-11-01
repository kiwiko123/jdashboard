package com.kiwiko.jdashboard.webapp.apps.games.state.internal;

import com.kiwiko.jdashboard.webapp.apps.games.state.data.UserGameStateAssociation;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.data.UserGameStateAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserGameStateAssociationEntityService implements UserGameStateAssociationService {

    @Inject private UserGameStateAssociationEntityDataFetcher dataFetcher;
    @Inject private UserGameStateAssociationEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Transactional(readOnly = true)
    @Override
    public Set<UserGameStateAssociation> findByUser(long userId) {
        return dataFetcher.findForUser(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserGameStateAssociation> findByGameStateAndUser(long gameStateId, long userId) {
        return dataFetcher.findForGameStateAndUser(gameStateId, userId)
                .map(mapper::toDto);
    }

    @Override
    public UserGameStateAssociation create(UserGameStateAssociation association) {
        return crudExecutor.create(association, dataFetcher, mapper);
    }
}
