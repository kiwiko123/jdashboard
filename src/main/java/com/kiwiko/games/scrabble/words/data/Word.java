package com.kiwiko.games.scrabble.words.data;

import com.kiwiko.persistence.identification.TypeIdentifiable;

public class Word extends TypeIdentifiable<Long> {

    private Long id;
    private String word;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), getWord());
    }
}
