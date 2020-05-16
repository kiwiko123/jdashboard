package com.kiwiko.webapp.games.state.internal;

import com.kiwiko.webapp.games.state.data.GameState;
import com.kiwiko.webapp.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.library.persistence.properties.api.EntityMapper;

public class GameStateEntityPropertyMapper extends EntityMapper<GameStateEntity, GameState> {

    @Override
    protected Class<GameStateEntity> getEntityType() {
        return GameStateEntity.class;
    }

    @Override
    protected Class<GameState> getDTOType() {
        return GameState.class;
    }
}
