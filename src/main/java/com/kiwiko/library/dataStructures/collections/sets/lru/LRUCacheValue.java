package com.kiwiko.library.dataStructures.collections.sets.lru;

import com.kiwiko.library.dataStructures.collections.lists.linked.Node;

class LRUCacheValue<E> {
    private final Node<E> node;

    public LRUCacheValue(Node<E> node) {
        this.node = node;
    }

    public Node<E> getNode() {
        return node;
    }
}
