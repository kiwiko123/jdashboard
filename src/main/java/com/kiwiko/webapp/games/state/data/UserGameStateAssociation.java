package com.kiwiko.webapp.games.state.data;

import com.kiwiko.library.persistence.identification.TypeIdentifiable;
import com.kiwiko.webapp.users.data.User;

public class UserGameStateAssociation extends TypeIdentifiable<Long> {

    private Long id;
    private User user;
    private GameState gameState;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
