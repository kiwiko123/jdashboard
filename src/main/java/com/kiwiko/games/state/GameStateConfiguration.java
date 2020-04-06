package com.kiwiko.games.state;

import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.internal.GameStateEntityPropertyMapper;
import com.kiwiko.games.state.internal.GameStateEntityService;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntityDAO;
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
}
