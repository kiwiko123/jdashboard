package com.kiwiko.library.dataStructures.collections.lists.sets;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class SetTest {

    private Set<String> stringSet;

    protected Set<String> createStringSet() {
        return new HashSet<>();
    }

    @Before
    public void setUp() {
        stringSet = createStringSet();
    }

    @Test
    public void testSize() {
        MatcherAssert.assertThat(stringSet, is(empty()));
        MatcherAssert.assertThat(stringSet, hasSize(0));

        stringSet.add("a");
        MatcherAssert.assertThat(stringSet, is(not(empty())));
        MatcherAssert.assertThat(stringSet, hasSize(1));

        stringSet.add("b");
        stringSet.add("c");
        stringSet.add("d");
        MatcherAssert.assertThat(stringSet, is(not(empty())));
        MatcherAssert.assertThat(stringSet, hasSize(4));

        stringSet.remove("a");
        stringSet.remove("b");
        MatcherAssert.assertThat(stringSet, is(not(empty())));
        MatcherAssert.assertThat(stringSet, hasSize(2));
    }

    @Test
    public void testContains() {
        MatcherAssert.assertThat("a", is(not(in(stringSet))));

        stringSet.add("a");
        MatcherAssert.assertThat("a", is(in(stringSet)));

        stringSet.add("b");
        stringSet.add("c");
        MatcherAssert.assertThat("b", is(in(stringSet)));
        MatcherAssert.assertThat("c", is(in(stringSet)));
        MatcherAssert.assertThat("d", is(not(in(stringSet))));
    }

    @Test
    public void testUniqueElements() {
        for (int i = 0; i < 100; ++i) {
            stringSet.add("a");
        }
        MatcherAssert.assertThat("a", is(in(stringSet)));
        MatcherAssert.assertThat(stringSet, hasSize(1));
    }

    @Test
    public void testAdd() {
        MatcherAssert.assertThat(stringSet, is(empty()));
        MatcherAssert.assertThat("a", is(not(in(stringSet))));

        Assert.assertTrue(stringSet.add("a"));
        MatcherAssert.assertThat(stringSet, hasSize(1));
        MatcherAssert.assertThat("a", is(in(stringSet)));

        Assert.assertTrue(stringSet.add("b"));
        MatcherAssert.assertThat(stringSet, hasSize(2));
        MatcherAssert.assertThat("b", is(in(stringSet)));
    }

    @Test
    public void testAdd_ignoresDuplicate() {
        Assert.assertTrue(stringSet.add("a"));
        MatcherAssert.assertThat(stringSet, hasSize(1));
        MatcherAssert.assertThat("a", is(in(stringSet)));

        Assert.assertFalse(stringSet.add("a"));
        MatcherAssert.assertThat(stringSet, hasSize(1));
        MatcherAssert.assertThat("a", is(in(stringSet)));
    }

    @Test
    public void testRemove() {
        Assert.assertTrue(stringSet.add("a"));
        Assert.assertTrue(stringSet.add("b"));
        Assert.assertTrue(stringSet.add("c"));
        MatcherAssert.assertThat(stringSet, hasSize(3));
        MatcherAssert.assertThat("a", is(in(stringSet)));
        MatcherAssert.assertThat("b", is(in(stringSet)));
        MatcherAssert.assertThat("c", is(in(stringSet)));

        Assert.assertTrue(stringSet.remove("c"));
        MatcherAssert.assertThat(stringSet, hasSize(2));
        MatcherAssert.assertThat("a", is(in(stringSet)));
        MatcherAssert.assertThat("b", is(in(stringSet)));
        MatcherAssert.assertThat("c", is(not(in(stringSet))));

        Assert.assertTrue(stringSet.remove("a"));
        MatcherAssert.assertThat(stringSet, hasSize(1));
        MatcherAssert.assertThat("a", is(not(in(stringSet))));
        MatcherAssert.assertThat("b", is(in(stringSet)));

        Assert.assertTrue(stringSet.remove("b"));
        MatcherAssert.assertThat(stringSet, is(empty()));
        MatcherAssert.assertThat("b", is(not(in(stringSet))));
    }

    @Test
    public void testRemove_nonContainedItem() {
        Assert.assertTrue(stringSet.add("a"));
        Assert.assertFalse(stringSet.remove("b"));
    }

    @Test
    public void testIterator_allIterationsContain() {
        Set<String> expectedSet = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        stringSet.addAll(expectedSet);
        Assert.assertEquals(expectedSet.size(), stringSet.size());

        String currentValue;
        Iterator<String> it = stringSet.iterator();

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertTrue(it.hasNext());
        currentValue = it.next();
        MatcherAssert.assertThat(currentValue, is(in(expectedSet)));
        expectedSet.remove(currentValue);

        Assert.assertFalse(it.hasNext());
        MatcherAssert.assertThat(expectedSet, is(empty()));
    }

    @Test
    public void testStream() {
        Set<String> expectedSet = Set.of("a", "b", "c", "d", "e", "f", "g", "h");
        stringSet.addAll(expectedSet);
        Assert.assertEquals(expectedSet.size(), stringSet.size());

        stringSet.stream()
                .forEach(value -> MatcherAssert.assertThat(value, is(in(expectedSet))));
    }
}
