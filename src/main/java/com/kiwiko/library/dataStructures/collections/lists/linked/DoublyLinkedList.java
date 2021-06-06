package com.kiwiko.library.dataStructures.collections.lists.linked;

import javax.annotation.Nullable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DoublyLinkedList<E> extends AbstractCollection<E> implements LinkedList<E> {
    private @Nullable Node<E> head;
    private @Nullable Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = Node.<E>newBuilder()
                .setValue(e)
                .build();

        if (isEmpty()) {
            head = newNode;
            tail = head;
        } else {
            Objects.requireNonNull(head, "Expected head to be non-null with non-empty list");
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = newNode;
        }

        ++size;
    }

    @Override
    public void addLast(E e) {
        if (isEmpty()) {
            addFirst(e);
            return;
        }

        Node<E> newNode = Node.<E>newBuilder()
                .setValue(e)
                .build();

        Objects.requireNonNull(tail, "Expected tail to be non-null with non-empty list");
        tail.setNext(newNode);
        newNode.setPrevious(tail);
        tail = newNode;

        ++size;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E getFirst() throws NoSuchElementException {
        return getHead().getValue();
    }

    @Override
    public E getLast() throws NoSuchElementException {
        return getTail().getValue();
    }

    @Override
    public Node<E> getHead() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Linked list is empty");
        }
        return head;
    }

    @Override
    public Node<E> getTail() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Linked list is empty");
        }
        return tail;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        E value = (E) o;
        return find(value) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeValueIterator<>(this, head);
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        E value = (E) o;
        Node<E> target = find(value);
        if (target == null) {
            return false;
        }

        removeNode(target);
        return true;
    }

    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out-of-bounds", index));
        }

        int i = 0;
        Node<E> current = head;
        while (current != null && i < size()) {
            if (i++ == index) {
                removeNode(current);
                return current.getValue();
            }
            current = current.getNext();
        }

        throw new IllegalStateException(String.format("Failed to find and remove node at index %d", index));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object value : c) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E value = it.next();
            if (filter.test(value)) {
                it.remove();
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return new java.util.LinkedList<>(this).stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return new java.util.LinkedList<>(this).parallelStream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoublyLinkedList)) {
            return false;
        }

        DoublyLinkedList<?> that = (DoublyLinkedList<?>) o;
        return size == that.size && Objects.equals(head, that.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail, size);
    }

    @Nullable
    protected Node<E> find(E value) {
        Node<E> current = head;
        while (current != null) {
            if (Objects.equals(value, current.getValue())) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    public void removeNode(Node<E> node) {
        Node<E> previous = node.getPrevious();
        Node<E> next = node.getNext();

        if (previous == null) {
            // This is the head of the list.
            if (next != null) {
                next.setPrevious(null);
            }
            head = next;
        } else {
            previous.setNext(next);
        }

        if (next == null) {
            // This is the tail of the list.
            if (previous != null) {
                previous.setNext(null);
            }
            tail = previous;
        } else {
            next.setPrevious(previous);
        }

        --size;
    }
}
