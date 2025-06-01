package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.unixlocal;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.api.WordService;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data.Word;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.WordEntityPropertyMapper;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntity;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess.WordEntityDAO;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnixLocalDictionaryWordService implements WordService {

    @Inject private WordEntityDAO wordEntityDAO;
    @Inject private WordEntityPropertyMapper mapper;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public Optional<Word> findByWord(String word) {
        return transactionProvider.readOnly(() -> wordEntityDAO.getByWord(word).map(mapper::toTargetType));
    }

    @Override
    public Collection<Word> findByWords(Collection<String> words) {
        return transactionProvider.readOnly(() -> wordEntityDAO.getByWords(words).stream()
                .map(mapper::toTargetType)
                .collect(Collectors.toSet()));
    }

    @Override
    public Word create(Word word) {
        WordEntity entityToCreate = mapper.toEntity(word);
        return transactionProvider.readWrite(() -> {
            WordEntity createdWord = wordEntityDAO.save(entityToCreate);
            return mapper.toDto(createdWord);
        });
    }

    @Override
    public List<Word> create(Collection<Word> words) {
        return transactionProvider.readWrite(() -> words.stream().map(this::create).toList());
    }
}
