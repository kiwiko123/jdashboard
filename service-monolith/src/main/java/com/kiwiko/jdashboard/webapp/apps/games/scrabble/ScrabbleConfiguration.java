package com.kiwiko.jdashboard.webapp.apps.games.scrabble;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.logic.ScrabbleGameHelper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.logic.ScrabbleMoveHelper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.internal.ScrabbleGameEntityService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.logic.ScrabbleCreateGameHelper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.WordsConfiguration;
import com.kiwiko.jdashboard.webapp.apps.games.state.GameStateConfiguration;
import com.kiwiko.jdashboard.webapp.framework.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dependency configuration.
 * Each method annotated with {@link Bean} returns the dependency's implementation.
 * Dependencies can be {@link javax.inject.Inject}ed into classes.
 */
@Configuration
public class ScrabbleConfiguration {

    @Bean
    @ConfiguredBy(GameStateConfiguration.class)
    public ScrabbleCreateGameHelper scrabbleCreateGameHelper() {
        return new ScrabbleCreateGameHelper();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ScrabbleGameHelper scrabbleGameHelper() {
        return new ScrabbleGameHelper();
    }

    @Bean
    @ConfiguredBy(WordsConfiguration.class)
    public ScrabbleMoveHelper scrabbleMoveHelper() {
        return new ScrabbleMoveHelper();
    }

    @Bean
    @ConfiguredBy({GameStateConfiguration.class, MvcConfiguration.class})
    public ScrabbleGameService scrabbleGameService() {
        return new ScrabbleGameEntityService();
    }
}
