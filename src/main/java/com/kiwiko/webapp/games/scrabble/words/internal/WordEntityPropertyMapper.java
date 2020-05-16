package com.kiwiko.webapp.games.scrabble.words.internal;

import com.kiwiko.webapp.games.scrabble.words.data.Word;
import com.kiwiko.webapp.games.scrabble.words.internal.dataAccess.WordEntity;
import com.kiwiko.library.lang.reflection.properties.api.FieldMapper;

import javax.inject.Singleton;

@Singleton
public class WordEntityPropertyMapper extends FieldMapper<WordEntity, Word> {

    @Override
    protected Class<Word> getTargetType() {
        return Word.class;
    }
}
