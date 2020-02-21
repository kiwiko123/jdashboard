package com.kiwiko.scrabble;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.scrabble.api.ScrabbleGameService;
import com.kiwiko.scrabble.game.helpers.ScrabbleGameHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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


    /**
     * Allows protected and public getters to be serialized as a web response.
     */
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);
    }
}
