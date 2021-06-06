package com.kiwiko.library.dataStructures.collections.lists.sets.lru;

import com.kiwiko.library.dataStructures.collections.lists.sets.SetTest;
import com.kiwiko.library.dataStructures.collections.sets.lru.LeastRecentlyUsedSet;

import java.util.Set;

public class LeastRecentlyUsedSetTest extends SetTest {

    @Override
    protected Set<String> createStringSet() {
        return new LeastRecentlyUsedSet<>(20);
    }
}
