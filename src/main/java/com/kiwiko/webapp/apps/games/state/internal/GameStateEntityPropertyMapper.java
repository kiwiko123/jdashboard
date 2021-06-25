package com.kiwiko.webapp.apps.games.state.internal;

import com.kiwiko.webapp.apps.games.state.data.GameState;
import com.kiwiko.webapp.apps.games.state.internal.dataAccess.GameStateEntity;
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
