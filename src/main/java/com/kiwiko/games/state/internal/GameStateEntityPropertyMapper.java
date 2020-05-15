package com.kiwiko.games.state.internal;

import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.persistence.properties.api.EntityMapper;

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
