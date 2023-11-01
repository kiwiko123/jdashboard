package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class WordEntity implements LongDataEntity {

    private Long id;
    private String word;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "word", unique = true, nullable = false)
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
