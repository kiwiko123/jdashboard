package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.api.WordService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data.Word;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntityDAO;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal.data.WordMigratorParameters;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class WordMigrator {

    private static final String WORDS_FILE_PATH = "/usr/share/dict/words";

    @Inject private WordEntityDAO wordEntityDAO;
    @Inject private WordService wordService;
    @Inject private Logger logger;

    public Collection<Long> process(WordMigratorParameters parameters) {
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(WORDS_FILE_PATH);
        Collection<Long> processedWordIds = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {

            Collection<String> wordsToAdd = new HashSet<>();
            String line;

            while ((line = reader.readLine()) != null) {
                int batchSize = parameters.getBatchSize();
                Integer maxWordsToProcess = parameters.getMaxWordsToProcess();

                if (maxWordsToProcess != null && processedWordIds.size() >= maxWordsToProcess) {
                    break;
                }

                if (maxWordsToProcess != null && batchSize + processedWordIds.size() > maxWordsToProcess) {
                    batchSize = maxWordsToProcess - processedWordIds.size();
                }

                if (wordsToAdd.size() >= batchSize) {
                    processBatch(processedWordIds, wordsToAdd);
                } else {
                    String word = line.trim();
                    if (!word.isEmpty()) {
                        wordsToAdd.add(word);
                    }
                }
            }

            // In case the number of remaining words of the last run was less than the batch size
            processBatch(processedWordIds, wordsToAdd);
        } catch (IOException e) {
            logger.error(String.format("Error reading file \"%s\"", WORDS_FILE_PATH), e);
        }

        return processedWordIds;
    }

    private void processBatch(Collection<Long> allProcessedWordIds, Collection<String> wordsToAdd) {
        Instant start = Instant.now();
        Collection<Long> wordIdsProcessedInBatch = wordService.createWords(wordsToAdd).stream()
                .map(Word::getId)
                .collect(Collectors.toSet());
        Instant end = Instant.now();
        allProcessedWordIds.addAll(wordIdsProcessedInBatch);

        Duration duration = Duration.between(start, end);
        logger.info(
                String.format("Created %d new words in %d ms; %d now processed in total", wordIdsProcessedInBatch.size(), duration.toMillis(), allProcessedWordIds.size()));

        wordsToAdd.clear();
    }
}
