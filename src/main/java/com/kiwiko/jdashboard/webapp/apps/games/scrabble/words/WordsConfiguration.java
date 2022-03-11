package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.api.WordService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal.UnixLocalDictionaryWordService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.WordEntityPropertyMapper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntityDAO;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal.WordMigrator;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordsConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public WordService wordService() {
        return new UnixLocalDictionaryWordService();
    }

    @Bean
    public WordEntityDAO wordEntityDAO() {
        return new WordEntityDAO();
    }

    @Bean
    public WordEntityPropertyMapper wordEntityPropertyMapper() {
        return new WordEntityPropertyMapper();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public WordMigrator wordMigrator() {
        return new WordMigrator();
    }
}
