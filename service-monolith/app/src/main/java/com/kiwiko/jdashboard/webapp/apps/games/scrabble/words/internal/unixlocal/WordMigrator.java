package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal;

import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.api.WordService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data.Word;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntityDAO;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal.data.WordMigratorParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WordMigrator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordMigrator.class);
    private static final String WORDS_FILE_PATH = "/usr/share/dict/words";

    @Inject private WordEntityDAO wordEntityDAO;
    @Inject private WordService wordService;

    public Set<Long> saveWords(WordMigratorParameters parameters) {
        Path file = Paths.get(WORDS_FILE_PATH);
        int saveBatchSize = 1000;
        Integer maxWordsToProcess = parameters.getMaxWordsToProcess();
        Set<Long> processedWordIds = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            Set<String> wordsToCreate = new HashSet<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (maxWordsToProcess != null && processedWordIds.size() > maxWordsToProcess) {
                    break;
                }

                String word = line.trim();
                if (word.isEmpty()) {
                    continue;
                }
                wordsToCreate.add(word);

                if (wordsToCreate.size() >= saveBatchSize) {
                    Set<String> existingWords = wordService.findByWords(wordsToCreate).stream()
                            .map(Word::getWord)
                            .collect(Collectors.toUnmodifiableSet());
                    Set<Word> wordObjectsToCreate = wordsToCreate.stream()
                            .filter(w -> !existingWords.contains(w))
                            .map(w -> {
                                Word w1 = new Word();
                                w1.setWord(w);
                                return w1;
                            })
                            .collect(Collectors.toUnmodifiableSet());
                    Set<Long> savedWordIds = wordService.create(wordObjectsToCreate).stream()
                            .map(Word::getId)
                            .collect(Collectors.toUnmodifiableSet());
                    LOGGER.info("Successfully saved {} new words", savedWordIds.size());

                    processedWordIds.addAll(savedWordIds);
                    wordsToCreate.clear();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read words file at: {}", WORDS_FILE_PATH);
        }

        return processedWordIds;
    }
}
