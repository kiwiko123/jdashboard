package com.kiwiko.games.state.internal.dataAccess;

import com.kiwiko.games.state.data.GameType;
import com.kiwiko.persistence.dataAccess.api.AuditableDataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "game_states")
public class GameStateEntity extends AuditableDataEntity {

    private Long id;
    private GameType gameType;
    private Long gameId;
    private String gameStateJson;
    private Instant createdDate;
    private Instant lastUpdatedDate;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_state_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Source: <a href="https://thoughts-on-java.org/hibernate-enum-mappings/" />
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
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

    @Column(name = "game_state_json", nullable = true)
    public String getGameStateJson() {
        return gameStateJson;
    }

    public void setGameStateJson(String gameStateJson) {
        this.gameStateJson = gameStateJson;
    }

    @Column(name = "created_date", nullable = false)
    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_updated_date", nullable = false)
    @Override
    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @Override
    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Column(name = "is_removed", nullable = false)
    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
