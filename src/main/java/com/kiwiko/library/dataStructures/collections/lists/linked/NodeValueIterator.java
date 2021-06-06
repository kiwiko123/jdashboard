package com.kiwiko.library.dataStructures.collections.lists.linked;

import java.util.Iterator;

public class NodeValueIterator<E> implements Iterator<E> {
    private final DoublyLinkedList<E> list;
    private Node<E> current;

    public NodeValueIterator(Node<E> current) {
        this(null, current);
    }

    NodeValueIterator(DoublyLinkedList<E> list, Node<E> current) {
        this.list = list;
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current.getNext() != null;
    }

    @Override
    public E next() {
        E value = current.getValue();
        current = current.getNext();
        return value;
    }

    @Override
    public void remove() {
        if (list == null) {
            throw new UnsupportedOperationException("Cannot operate on list");
        }
        list.removeNode(current);
    }
}
