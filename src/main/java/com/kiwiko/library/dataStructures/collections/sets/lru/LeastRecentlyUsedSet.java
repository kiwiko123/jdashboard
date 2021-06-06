package com.kiwiko.library.dataStructures.collections.sets.lru;

import com.kiwiko.library.dataStructures.collections.lists.linked.DoublyLinkedList;
import com.kiwiko.library.dataStructures.collections.lists.linked.Node;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class LeastRecentlyUsedSet<E> extends AbstractSet<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private final DoublyLinkedList<E> list;
    private final Map<E, LRUCacheValue<E>> table;
    private int capacity;

    public LeastRecentlyUsedSet() {
        this(DEFAULT_CAPACITY);
    }

    public LeastRecentlyUsedSet(int capacity) {
        this(Collections.emptyList(), capacity);
    }

    public LeastRecentlyUsedSet(Iterable<E> iterable) {
        this(iterable, DEFAULT_CAPACITY);
    }

    public LeastRecentlyUsedSet(Iterable<E> iterable, int capacity) {
        // TODO add iterable
        list = new DoublyLinkedList<>();
        table = new HashMap<>();
        this.capacity = capacity;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        E value = (E) o;
        if (!table.containsKey(value)) {
            return false;
        }

        moveToHead(value);
        return true;
    }

    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }

        list.addFirst(e);
        LRUCacheValue<E> cacheValue = new LRUCacheValue<>(list.getHead());
        table.put(e, cacheValue);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        E value = (E) o;
        if (!table.containsKey(value)) {
            return false;
        }

        LRUCacheValue<E> cacheValue = table.remove(value);
        list.removeNode(cacheValue.getNode());
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
        table.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, table, capacity);
    }

    /**
     * Moves the given value to the head of the list.
     * Used when a value is the most recently used item in the cache.
     *
     * @param e the value to move
     * @return true if the cache was modified, or false if no changes were made
     */
    protected boolean moveToHead(E e) {
        LRUCacheValue<E> cacheValue = table.get(e);
        if (cacheValue == null) {
            return false;
        }

        Node<E> node = cacheValue.getNode();
        if (node == list.getHead()) {
            return false;
        }

        list.removeNode(node);
        list.addFirst(e);
        return true;
    }
}
