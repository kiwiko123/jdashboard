package com.kiwiko.webapp.games.state.internal.dataAccess;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_game_state_associations")
public class UserGameStateAssociationEntity extends DataEntity {

    private Long id;
    private long userId;
    private long gameStateId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "association_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "game_state_id", nullable = false)
    public long getGameStateId() {
        return gameStateId;
    }

    public void setGameStateId(long gameStateId) {
        this.gameStateId = gameStateId;
    }
}
