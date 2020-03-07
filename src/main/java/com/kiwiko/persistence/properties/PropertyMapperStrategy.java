package com.kiwiko.persistence.properties;

public abstract class PropertyMapperStrategy<I, O> {

    public abstract O map(I input);

    public boolean canMap(I input) {
        return true;
    }
}
