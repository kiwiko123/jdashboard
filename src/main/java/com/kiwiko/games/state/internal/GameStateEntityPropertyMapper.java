package com.kiwiko.games.state.internal;

import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;

public class GameStateEntityPropertyMapper extends BidirectionalFieldMapper<GameStateEntity, GameState> {

    @Override
    protected Class<GameStateEntity> getSourceType() {
        return GameStateEntity.class;
    }

    @Override
    protected Class<GameState> getTargetType() {
        return GameState.class;
    }
}
