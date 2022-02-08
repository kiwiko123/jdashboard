package com.kiwiko.jdashboard.library.dataStructures.collections.sets.lru;

import com.kiwiko.jdashboard.library.dataStructures.collections.lists.linked.Node;

import javax.annotation.Nullable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LeastRecentlyUsedSetIterator<E> implements Iterator<E> {
    private final LeastRecentlyUsedSet<E> ref;
    private @Nullable Node<E> current;
    private int expectedSize;

    LeastRecentlyUsedSetIterator(LeastRecentlyUsedSet<E> ref, Node<E> current) {
        this.ref = ref;
        this.current = current;
        expectedSize = ref.size();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public E next() {
        if (current == null) {
            throw new NoSuchElementException();
        }

        E value = current.getValue();
        current = current.getNext();
        return value;
    }

    @Override
    public void remove() {
        if (expectedSize-- != ref.size()) {
            throw new ConcurrentModificationException();
        }

        E currentValue = next();
        ref.remove(currentValue);
    }
}
