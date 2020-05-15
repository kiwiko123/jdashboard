package com.kiwiko.games.state;

import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.internal.GameStateEntityPropertyMapper;
import com.kiwiko.games.state.internal.GameStateEntityService;
import com.kiwiko.games.state.internal.UserGameStateAssociationEntityFieldMapper;
import com.kiwiko.games.state.internal.UserGameStateAssociationEntityService;
import com.kiwiko.games.state.internal.UserGameStateAssociationService;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntityDAO;
import com.kiwiko.games.state.internal.dataAccess.UserGameStateAssociationEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameStateConfiguration {

    @Bean
    public GameStateEntityDAO gameStateEntityDAO() {
        return new GameStateEntityDAO();
    }

    @Bean
    public GameStateService gameStateService() {
        return new GameStateEntityService();
    }

    @Bean
    public GameStateEntityPropertyMapper gameStateEntityPropertyMapper() {
        return new GameStateEntityPropertyMapper();
    }

    @Bean
    public UserGameStateAssociationEntityDAO userGameStateAssociationEntityDAO() {
        return new UserGameStateAssociationEntityDAO();
    }

    @Bean
    public UserGameStateAssociationService userGameStateAssociationService() {
        return new UserGameStateAssociationEntityService();
    }

    @Bean
    public UserGameStateAssociationEntityFieldMapper userGameStateAssociationEntityFieldMapper() {
        return new UserGameStateAssociationEntityFieldMapper();
    }
}
