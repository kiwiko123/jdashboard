package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.api;

import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data.Word;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WordService {

    Optional<Word> findByWord(String word);

    Collection<Word> findByWords(Collection<String> words);

    Word create(Word word);

    List<Word> create(Collection<Word> words);
}
