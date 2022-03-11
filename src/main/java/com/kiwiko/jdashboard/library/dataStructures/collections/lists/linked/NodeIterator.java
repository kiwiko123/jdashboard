package com.kiwiko.jdashboard.library.dataStructures.collections.lists.linked;

import java.util.Iterator;

public class NodeIterator<E> implements Iterator<Node<E>> {
    private Node<E> current;

    public NodeIterator(Node<E> current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current.getNext() != null;
    }

    @Override
    public Node<E> next() {
        Node<E> save = current;
        current = current.getNext();
        return save;
    }
}
