package com.kiwiko.library.dataStructures.collections.lists.sets;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
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
}
