package com.kiwiko.webapp.apps.games.state.internal.data;

import com.kiwiko.library.persistence.data.api.interfaces.SoftDeletableDataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_states")
@CaptureDataChanges
public class GameStateEntity implements SoftDeletableDataEntity {

    private Long id;
    private String gameType;
    private Long gameId;
    private String gameStateJson;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "game_type", nullable = false)
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    @Nullable
    @Column(name = "game_state_json")
    public String getGameStateJson() {
        return gameStateJson;
    }

    public void setGameStateJson(@Nullable String gameStateJson) {
        this.gameStateJson = gameStateJson;
    }

    @Override
    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
