package com.kiwiko.jdashboard.webapp.apps.games.state.data;

import com.kiwiko.library.persistence.data.api.interfaces.DataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class GameState extends DataEntityDTO {

    private String gameType;
    private long gameId;
    private @Nullable String gameStateJson;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
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

    @Deprecated
    public Instant getCreatedDate() {
        return Instant.now();
    }
}
