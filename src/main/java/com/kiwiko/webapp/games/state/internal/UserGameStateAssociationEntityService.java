package com.kiwiko.webapp.games.state.internal;

import com.kiwiko.webapp.games.state.data.UserGameStateAssociation;
import com.kiwiko.webapp.games.state.internal.dataAccess.UserGameStateAssociationEntity;
import com.kiwiko.webapp.games.state.internal.dataAccess.UserGameStateAssociationEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserGameStateAssociationEntityService implements UserGameStateAssociationService {

    @Inject
    private UserGameStateAssociationEntityDAO userGameStateAssociationEntityDAO;

    @Inject
    private UserGameStateAssociationEntityFieldMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Collection<UserGameStateAssociation> findByUser(long userId) {
        return userGameStateAssociationEntityDAO.findForUser(userId).stream()
                .map(mapper::toTargetType)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserGameStateAssociation> findByGameStateAndUser(long gameStateId, long userId) {
        return userGameStateAssociationEntityDAO.findForGameStateAndUser(gameStateId, userId)
                .map(mapper::toTargetType);
    }

    @Transactional
    @Override
    public UserGameStateAssociation create(long gameStateId, long userId) {
        UserGameStateAssociationEntity entity = new UserGameStateAssociationEntity();
        entity.setGameStateId(gameStateId);
        entity.setUserId(userId);

        UserGameStateAssociationEntity managedEntity = userGameStateAssociationEntityDAO.save(entity);
        return mapper.toTargetType(managedEntity);
    }
}
