package com.kiwiko.webapp.apps.games.state.data;

import com.kiwiko.library.persistence.dataAccess.data.AuditableDataEntityDTO;

import javax.annotation.Nullable;
import java.util.Optional;

public class GameState extends AuditableDataEntityDTO {

    private GameType gameType;
    private long gameId;
    private @Nullable String gameStateJson;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Optional<String> getGameStateJson() {
        return Optional.ofNullable(gameStateJson);
    }

    public void setGameStateJson(@Nullable String gameStateJson) {
        this.gameStateJson = gameStateJson;
    }
}
