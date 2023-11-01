package com.kiwiko.jdashboard.webapp.apps.games.state.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;

public class UserGameStateAssociation extends DataEntityDTO {

    private Long id;
    private Long userId;
    private Long gameStateId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameStateId() {
        return gameStateId;
    }

    public void setGameStateId(Long gameStateId) {
        this.gameStateId = gameStateId;
    }
}
