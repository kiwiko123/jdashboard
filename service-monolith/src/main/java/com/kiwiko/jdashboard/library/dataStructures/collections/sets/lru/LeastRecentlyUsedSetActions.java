package com.kiwiko.jdashboard.library.dataStructures.collections.sets.lru;

import com.kiwiko.jdashboard.library.dataStructures.collections.CollectionActions;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class LeastRecentlyUsedSetActions<E> extends CollectionActions<E> {
    private @Nullable Consumer<E> onEvict;

    @Nullable
    public Consumer<E> getOnEvict() {
        return onEvict;
    }

    public void setOnEvict(@Nullable Consumer<E> onEvict) {
        this.onEvict = onEvict;
    }
}
