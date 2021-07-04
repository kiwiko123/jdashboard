package com.kiwiko.library.dataStructures.collections.lists.linked;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DoublyLinkedListTest {

    private DoublyLinkedList<String> stringIntList;

    @Before
    public void setUp() {
        stringIntList = new DoublyLinkedList<>();
    }

    @Test
    public void testSize() {
        MatcherAssert.assertThat(stringIntList, is(empty()));
        MatcherAssert.assertThat(stringIntList, hasSize(0));

        stringIntList.add("1");
        MatcherAssert.assertThat(stringIntList, is(not(empty())));
        MatcherAssert.assertThat(stringIntList, hasSize(1));

        stringIntList.add("2");
        stringIntList.add("3");
        stringIntList.add("4");
        stringIntList.add("5");

        MatcherAssert.assertThat(stringIntList, is(not(empty())));
        MatcherAssert.assertThat(stringIntList, hasSize(5));
    }

    @Test
    public void testAddFirst() {
        stringIntList.addFirst("1");
        MatcherAssert.assertThat(stringIntList.getFirst(), is("1"));
        MatcherAssert.assertThat(stringIntList.getHead().getValue(), is("1"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("1"));
        MatcherAssert.assertThat(stringIntList.getTail().getValue(), is("1"));

        stringIntList.addFirst("2");
        MatcherAssert.assertThat(stringIntList.getFirst(), is("2"));
        MatcherAssert.assertThat(stringIntList.getHead().getValue(), is("2"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("1"));
        MatcherAssert.assertThat(stringIntList.getTail().getValue(), is("1"));
    }

    @Test
    public void testAddLast() {
        stringIntList.add("1");
        MatcherAssert.assertThat(stringIntList.getFirst(), is("1"));
        MatcherAssert.assertThat(stringIntList.getHead().getValue(), is("1"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("1"));
        MatcherAssert.assertThat(stringIntList.getTail().getValue(), is("1"));

        stringIntList.addLast("2");
        MatcherAssert.assertThat(stringIntList.getFirst(), is("1"));
        MatcherAssert.assertThat(stringIntList.getHead().getValue(), is("1"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("2"));
        MatcherAssert.assertThat(stringIntList.getTail().getValue(), is("2"));
    }

    @Test
    public void testContains() {
        MatcherAssert.assertThat("0", is(not(in(stringIntList))));
        MatcherAssert.assertThat("1", is(not(in(stringIntList))));
        MatcherAssert.assertThat("2", is(not(in(stringIntList))));

        stringIntList.add("0");
        MatcherAssert.assertThat("0", is(in(stringIntList)));
        MatcherAssert.assertThat("1", is(not(in(stringIntList))));
        MatcherAssert.assertThat("2", is(not(in(stringIntList))));

        stringIntList.add("1");
        MatcherAssert.assertThat("1", is(in(stringIntList)));
        MatcherAssert.assertThat("2", is(not(in(stringIntList))));

        stringIntList.add("2");
        MatcherAssert.assertThat("2", is(in(stringIntList)));
    }

    @Test
    public void testRemove() {
        MatcherAssert.assertThat(stringIntList, hasSize(0));
        MatcherAssert.assertThat(stringIntList, is(empty()));

        Assert.assertFalse(stringIntList.remove("0"));
        MatcherAssert.assertThat(stringIntList, hasSize(0));
        MatcherAssert.assertThat(stringIntList, is(empty()));

        stringIntList.add("1");
        MatcherAssert.assertThat(stringIntList, hasSize(1));
        MatcherAssert.assertThat(stringIntList, is(not(empty())));
        MatcherAssert.assertThat(stringIntList.getFirst(), is("1"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("1"));

        Assert.assertTrue(stringIntList.remove("1"));
        MatcherAssert.assertThat(stringIntList, hasSize(0));
        MatcherAssert.assertThat(stringIntList, is(empty()));
        Assert.assertThrows(NoSuchElementException.class, stringIntList::getFirst);
        Assert.assertThrows(NoSuchElementException.class, stringIntList::getLast);

        stringIntList.add("2");
        stringIntList.add("3");
        stringIntList.add("4");
        MatcherAssert.assertThat(stringIntList, hasSize(3));
        Assert.assertTrue(stringIntList.remove("3"));
        MatcherAssert.assertThat(stringIntList.getFirst(), is("2"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("4"));

        Assert.assertTrue(stringIntList.remove("4"));
        MatcherAssert.assertThat(stringIntList, hasSize(1));
        MatcherAssert.assertThat(stringIntList.getFirst(), is("2"));
        MatcherAssert.assertThat(stringIntList.getLast(), is("2"));

        Assert.assertTrue(stringIntList.remove("2"));
        MatcherAssert.assertThat(stringIntList, hasSize(0));
        MatcherAssert.assertThat(stringIntList, is(empty()));
        Assert.assertThrows(NoSuchElementException.class, stringIntList::getFirst);
        Assert.assertThrows(NoSuchElementException.class, stringIntList::getLast);

        Assert.assertFalse(stringIntList.remove("2"));
    }

    @Test
    public void testRemove_index() {
        stringIntList.add("0");
        stringIntList.add("1");
        stringIntList.add("2");
        stringIntList.add("3");
        MatcherAssert.assertThat(stringIntList, hasSize(4));

        Assert.assertEquals("0", stringIntList.remove(0));
        MatcherAssert.assertThat(stringIntList, hasSize(3));
        MatcherAssert.assertThat("0", not(is(in(stringIntList))));
        Assert.assertEquals("1", stringIntList.getFirst());

        Assert.assertEquals("2", stringIntList.remove(1));
        MatcherAssert.assertThat(stringIntList, hasSize(2));
        MatcherAssert.assertThat("2", not(is(in(stringIntList))));
        Assert.assertEquals("1", stringIntList.getFirst());

        Assert.assertEquals("3", stringIntList.remove(1));
        MatcherAssert.assertThat(stringIntList, hasSize(1));
        MatcherAssert.assertThat("3", not(is(in(stringIntList))));
        Assert.assertEquals("1", stringIntList.getFirst());

        Assert.assertThrows(IndexOutOfBoundsException.class, () -> stringIntList.remove(10));

        Assert.assertEquals("1", stringIntList.remove(0));
        MatcherAssert.assertThat(stringIntList, is(empty()));
        MatcherAssert.assertThat("1", not(is(in(stringIntList))));
        Assert.assertThrows(NoSuchElementException.class, stringIntList::getFirst);
    }
}
