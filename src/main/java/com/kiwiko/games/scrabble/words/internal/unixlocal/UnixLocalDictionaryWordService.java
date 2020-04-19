package com.kiwiko.games.scrabble.words.internal.unixlocal;

import com.kiwiko.games.scrabble.words.api.WordService;
import com.kiwiko.games.scrabble.words.data.Word;
import com.kiwiko.games.scrabble.words.internal.WordEntityPropertyMapper;
import com.kiwiko.games.scrabble.words.internal.dataAccess.WordEntity;
import com.kiwiko.games.scrabble.words.internal.dataAccess.WordEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UnixLocalDictionaryWordService implements WordService {

    @Inject
    private WordEntityDAO wordEntityDAO;

    @Inject
    private WordEntityPropertyMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<Word> findByWord(String word) {
        return wordEntityDAO.getByWord(word)
                .map(mapper::toTargetType);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Word> findByWords(Collection<String> words) {
        return wordEntityDAO.getByWords(words).stream()
                .map(mapper::toTargetType)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Word> getByIds(Collection<Long> wordIds) {
        return wordEntityDAO.getByIds(wordIds).stream()
                .map(mapper::toTargetType)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public Optional<Word> createWord(String word) {
        WordEntity entity = new WordEntity();
        entity.setWord(word);

        WordEntity managedEntity = wordEntityDAO.save(entity);
        return Optional.of(mapper.toTargetType(managedEntity));
    }

    @Transactional
    @Override
    public Collection<Word> createWords(Collection<String> words) {
        Set<String> existingWords = findByWords(words).stream()
                .map(Word::getWord)
                .collect(Collectors.toSet());

        return words.stream()
                .filter(word -> !existingWords.contains(word))
                .map(this::createWord)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
