package com.kiwiko.jdashboard.library.dataStructures.collections;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CollectionActions<E> {
    private @Nullable Consumer<E> onContains;
    private @Nullable Consumer<E> onAdd;
    private @Nullable Consumer<E> onRemove;

    @Nullable
    public Consumer<E> getOnContains() {
        return onContains;
    }

    public void setOnContains(@Nullable Consumer<E> onContains) {
        this.onContains = onContains;
    }

    @Nullable
    public Consumer<E> getOnAdd() {
        return onAdd;
    }

    public void setOnAdd(@Nullable Consumer<E> onAdd) {
        this.onAdd = onAdd;
    }

    @Nullable
    public Consumer<E> getOnRemove() {
        return onRemove;
    }

    public void setOnRemove(@Nullable Consumer<E> onRemove) {
        this.onRemove = onRemove;
    }
}
