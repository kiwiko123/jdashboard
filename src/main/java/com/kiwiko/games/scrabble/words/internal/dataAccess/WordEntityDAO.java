package com.kiwiko.games.scrabble.words.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.EntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class WordEntityDAO extends EntityManagerDAO<WordEntity> {

    @Override
    protected Class<WordEntity> getEntityType() {
        return WordEntity.class;
    }

    public Optional<WordEntity> getByWord(String word) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<WordEntity> query = builder.createQuery(entityType);
        Root<WordEntity> root = query.from(entityType);
        Expression<String> wordField = root.get("word");
        Expression<String> lowerCaseWordField = builder.lower(wordField);
        Predicate equalsWord = builder.equal(lowerCaseWordField, word.toLowerCase());

        query.where(equalsWord);
        return getSingleResult(query);
    }

    public Collection<WordEntity> getByWords(Collection<String> words) {
        // There's an index on lower(word)
        Set<String> lowerCaseWords = words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<WordEntity> query = builder.createQuery(entityType);
        Root<WordEntity> root = query.from(entityType);
        Expression<String> wordField = root.get("word");
        Expression<String> lowerCaseWordField = builder.lower(wordField);
        Predicate wordInWords = lowerCaseWordField.in(lowerCaseWords);

        query.where(wordInWords);
        return createQuery(query).getResultList();
    }
}
