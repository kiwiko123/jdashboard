package com.kiwiko.jdashboard.webapp.apps.games.state;

import com.kiwiko.jdashboard.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.GameStateEntityMapper;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.GameStateEntityService;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.UserGameStateAssociationEntityMapper;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.UserGameStateAssociationEntityService;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.UserGameStateAssociationService;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.data.GameStateEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.data.UserGameStateAssociationEntityDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameStateConfiguration {

    @Bean
    public GameStateEntityDataFetcher gameStateEntityDAO() {
        return new GameStateEntityDataFetcher();
    }

    @Bean
    public GameStateService gameStateService() {
        return new GameStateEntityService();
    }

    @Bean
    public GameStateEntityMapper gameStateEntityPropertyMapper() {
        return new GameStateEntityMapper();
    }

    @Bean
    public UserGameStateAssociationEntityDataFetcher userGameStateAssociationEntityDAO() {
        return new UserGameStateAssociationEntityDataFetcher();
    }

    @Bean
    public UserGameStateAssociationService userGameStateAssociationService() {
        return new UserGameStateAssociationEntityService();
    }

    @Bean
    public UserGameStateAssociationEntityMapper userGameStateAssociationEntityFieldMapper() {
        return new UserGameStateAssociationEntityMapper();
    }
}
