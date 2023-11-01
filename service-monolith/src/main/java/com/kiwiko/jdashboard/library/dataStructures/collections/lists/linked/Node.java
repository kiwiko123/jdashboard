package com.kiwiko.jdashboard.library.dataStructures.collections.lists.linked;

import javax.annotation.Nullable;
import java.util.Objects;

public class Node<E> {
    public static <E> Builder<E> newBuilder() {
        return new Builder<>();
    }

    private final @Nullable E value;
    private @Nullable Node<E> previous;
    private @Nullable Node<E> next;

    Node(@Nullable E value, @Nullable Node<E> previous, @Nullable Node<E> next) {
        this.value = value;
        this.previous = previous;
        this.next = next;
    }

    @Nullable
    public E getValue() {
        return value;
    }

    @Nullable
    public Node<E> getPrevious() {
        return previous;
    }

    void setPrevious(@Nullable Node<E> previous) {
        this.previous = previous;
    }

    @Nullable
    public Node<E> getNext() {
        return next;
    }

    void setNext(@Nullable Node<E> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value)
                && Objects.equals(previous, node.previous)
                && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, previous, next);
    }

    public static final class Builder<T> {
        private @Nullable T value;
        private @Nullable Node<T> previous;
        private @Nullable Node<T> next;

        @Nullable
        public T getValue() {
            return value;
        }

        public Builder<T> setValue(@Nullable T value) {
            this.value = value;
            return this;
        }

        @Nullable
        public Node<T> getPrevious() {
            return previous;
        }

        public Builder<T> setPrevious(@Nullable Node<T> previous) {
            this.previous = previous;
            return this;
        }

        @Nullable
        public Node<T> getNext() {
            return next;
        }

        public Builder<T> setNext(@Nullable Node<T> next) {
            this.next = next;
            return this;
        }

        public Node<T> build() {
            return new Node<>(value, previous, next);
        }
    }
}
