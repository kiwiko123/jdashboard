package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal;

import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityFieldMapper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data.Word;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntity;

import javax.inject.Singleton;

@Singleton
public class WordEntityPropertyMapper extends DataEntityFieldMapper<WordEntity, Word> {
}
