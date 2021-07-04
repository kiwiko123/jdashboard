package com.kiwiko.webapp.apps.games.scrabble.words.api;

import com.kiwiko.webapp.apps.games.scrabble.words.data.Word;

import java.util.Collection;
import java.util.Optional;

public interface WordService {

    Optional<Word> findByWord(String word);

    Collection<Word> findByWords(Collection<String> words);

    Collection<Word> getByIds(Collection<Long> wordIds);

    Optional<Word> createWord(String word);

    Collection<Word> createWords(Collection<String> words);
}
