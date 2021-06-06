package com.kiwiko.library.dataStructures.collections.lists.linked;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface LinkedList<E> extends Collection<E> {

    void addFirst(E e);

    void addLast(E e);

    E getFirst() throws NoSuchElementException;

    E getLast() throws NoSuchElementException;

    Node<E> getHead() throws NoSuchElementException;

    Node<E> getTail() throws NoSuchElementException;
}
