package com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces;

public interface ObjectMerger<T> {

    /**
     * @param input the input object to consume from
     * @param target the target object to update from the input
     * @return the target object
     */
    T merge(T input, T target) throws ObjectMergingException;
}
