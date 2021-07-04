package com.kiwiko.webapp.apps.games.scrabble.words.web;

import com.kiwiko.webapp.apps.games.scrabble.words.api.WordService;
import com.kiwiko.webapp.apps.games.scrabble.words.internal.unixlocal.WordMigrator;
import com.kiwiko.webapp.apps.games.scrabble.words.internal.unixlocal.data.WordMigratorParameters;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Collection;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@RestController
public class WordsInternalAPIController {

    @Inject
    private WordMigrator wordMigrator;

    @Inject
    private WordService wordService;

    @PostMapping("/words/api/internal/migrate")
    public ResponsePayload migrateWords(
            @RequestParam(name = "batchSize") int batchSize,
            @RequestParam(name = "maxWordsToProcess", required = false) @Nullable Integer maxWordsToProcess) {
        WordMigratorParameters parameters = new WordMigratorParameters()
                .withBatchSize(batchSize)
                .withMaxWordsToProcess(maxWordsToProcess);
        Collection<Long> migratedWordIds = wordMigrator.process(parameters);

        return new ResponseBuilder()
                .withBody(migratedWordIds)
                .build();
    }
}
