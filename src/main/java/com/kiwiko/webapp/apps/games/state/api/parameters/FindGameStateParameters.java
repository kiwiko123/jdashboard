package com.kiwiko.webapp.apps.games.state.api.parameters;

import javax.annotation.Nullable;

public class FindGameStateParameters {

    private @Nullable String gameType;
    private @Nullable Long gameId;
    private @Nullable Long userId;

    @Nullable
    public String getGameType() {
        return gameType;
    }

    public FindGameStateParameters setGameType(@Nullable String gameType) {
        this.gameType = gameType;
        return this;
    }

    @Nullable
    public Long getGameId() {
        return gameId;
    }

    public FindGameStateParameters setGameId(@Nullable Long gameId) {
        this.gameId = gameId;
        return this;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public FindGameStateParameters setUserId(@Nullable Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "FindGameStateParameters{" +
                "gameType='" + gameType + '\'' +
                ", gameId=" + gameId +
                ", userId=" + userId +
                '}';
    }
}
