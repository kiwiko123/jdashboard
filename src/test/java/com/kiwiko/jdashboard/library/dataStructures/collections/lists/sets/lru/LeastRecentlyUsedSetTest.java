package com.kiwiko.jdashboard.library.dataStructures.collections.lists.sets.lru;

import com.kiwiko.jdashboard.library.dataStructures.collections.lists.sets.SetTest;
import com.kiwiko.jdashboard.library.dataStructures.collections.sets.lru.LeastRecentlyUsedSet;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LeastRecentlyUsedSetTest extends SetTest {

    private LeastRecentlyUsedSet<String> stringSet;

    @Override
    protected Set<String> createStringSet() {
        return new LeastRecentlyUsedSet<>(20);
    }

    @Before
    public void setUp() {
        super.setUp();
        stringSet = new LeastRecentlyUsedSet<>(20);
    }

    @Test
    public void testEvictWhenCapacityIsExceeded() {
        stringSet.setCapacity(3);

        Assert.assertTrue(stringSet.add("a"));
        MatcherAssert.assertThat(stringSet, hasSize(1));
        MatcherAssert.assertThat("a", is(in(stringSet)));

        Assert.assertTrue(stringSet.add("b"));
        MatcherAssert.assertThat(stringSet, hasSize(2));
        MatcherAssert.assertThat("b", is(in(stringSet)));

        Assert.assertTrue(stringSet.add("c"));
        MatcherAssert.assertThat(stringSet, hasSize(3));
        MatcherAssert.assertThat("c", is(in(stringSet)));

        Assert.assertTrue(stringSet.add("d"));
        MatcherAssert.assertThat(stringSet, hasSize(3));
        MatcherAssert.assertThat("d", is(in(stringSet)));
        MatcherAssert.assertThat("a", is(not(in(stringSet))));
    }

    @Test
    public void testIterator_remove() {
        Set<String> expectedSet = Set.of("a", "b", "c", "d", "e");
        stringSet.addAll(expectedSet);
        Assert.assertEquals(expectedSet.size(), stringSet.size());

        Iterator<String> it = stringSet.iterator();

        Assert.assertTrue(it.hasNext());
        it.remove();
        MatcherAssert.assertThat(stringSet, hasSize(4));

        Assert.assertTrue(it.hasNext());
        it.remove();
        MatcherAssert.assertThat(stringSet, hasSize(3));

        Assert.assertTrue(it.hasNext());
        it.remove();
        MatcherAssert.assertThat(stringSet, hasSize(2));

        Assert.assertTrue(it.hasNext());
        it.remove();
        MatcherAssert.assertThat(stringSet, hasSize(1));

        Assert.assertTrue(it.hasNext());
        it.remove();
        MatcherAssert.assertThat(stringSet, is(empty()));

        Assert.assertFalse(it.hasNext());
        Assert.assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    public void testIterator_remove_concurrentModificationThrowsException() {
        Set<String> expectedSet = Set.of("a", "b", "c");
        stringSet.addAll(expectedSet);
        Assert.assertEquals(expectedSet.size(), stringSet.size());

        Iterator<String> it = stringSet.iterator();

        Assert.assertTrue(it.hasNext());

        stringSet.remove("b");
        MatcherAssert.assertThat(stringSet, hasSize(2));
        Assert.assertThrows(ConcurrentModificationException.class, it::remove);
    }
}
