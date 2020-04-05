package com.kiwiko.scrabble;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.scrabble.api.ScrabbleGameService;
import com.kiwiko.scrabble.game.helpers.ScrabbleGameHelper;
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
    public ScrabbleGameHelper scrabbleGameHelper() {
        return new ScrabbleGameHelper();
    }

    @Bean
    public ScrabbleGameService scrabbleGameService(
            CacheService cacheService,
            ScrabbleGameHelper scrabbleGameHelper) {
        return new ScrabbleGameService(cacheService, scrabbleGameHelper);
    }
}
