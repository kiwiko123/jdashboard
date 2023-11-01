package com.kiwiko.jdashboard.library.dataStructures.maps;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MapTest {

    private Map<String, String> stringMap;

    protected Map<String, String> createStringMap() {
        return new HashMap<>();
    }

    @Before
    public void setUp() {
        stringMap = createStringMap();
    }

    @Test
    public void testSize() {
        MatcherAssert.assertThat(stringMap, is(anEmptyMap()));

        stringMap.put("A", "1");
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));

        stringMap.put("B", "2");
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(2)));
    }

    @Test
    public void testPut() {
        stringMap.put("A", "1");
        MatcherAssert.assertThat(stringMap, hasEntry("A", "1"));
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));

        stringMap.put("A", "2");
        MatcherAssert.assertThat(stringMap, hasEntry("A", "2"));
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));

        stringMap.put("B", "10");
        MatcherAssert.assertThat(stringMap, hasEntry("B", "10"));
        MatcherAssert.assertThat(stringMap, hasEntry("A", "2"));
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(2)));
    }

    @Test
    public void testContainsKey() {
        MatcherAssert.assertThat(stringMap, not(hasKey("A")));

        stringMap.put("A", "1");
        MatcherAssert.assertThat(stringMap, hasKey("A"));

        stringMap.put("B", "2");
        MatcherAssert.assertThat(stringMap, hasKey("B"));

        stringMap.remove("A");
        MatcherAssert.assertThat(stringMap, not(hasKey("A")));
    }

    @Test
    public void testGet_validKey() {
        stringMap.put("A", "1");
        String value = stringMap.get("A");
        Assert.assertEquals("1", value);

        stringMap.put("B", "2");
        value = stringMap.get("B");
        Assert.assertEquals("2", value);

        stringMap.put("C", null);
        value = stringMap.get("C");
        Assert.assertNull(value);
    }

    @Test
    public void testGet_invalidKey() {
        String value = stringMap.get("A");
        Assert.assertNull(value);
    }

    @Test
    public void testRemove_validKey() {
        stringMap.put("A", "1");
        stringMap.put("B", "2");
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(2)));

        String removedValue = stringMap.remove("A");
        Assert.assertEquals("1", removedValue);
        MatcherAssert.assertThat(stringMap, not(hasKey("A")));
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));
        MatcherAssert.assertThat(stringMap, hasEntry("B", "2"));
    }

    @Test
    public void testRemove_nonPresentKey() {
        stringMap.put("A", "1");
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));

        String removedValue = stringMap.remove("B");
        Assert.assertNull(removedValue);
        MatcherAssert.assertThat(stringMap, is(aMapWithSize(1)));
    }

    @Test
    public void testKeySet() {
        stringMap.put("A", "1");
        stringMap.put("B", "2");
        stringMap.put("C", "3");
        stringMap.put("D", "4");
        stringMap.put("E", "5");

        Set<String> keys = stringMap.keySet();
        Assert.assertEquals(Set.of("A", "B", "C", "D", "E"), keys);
    }

    @Test
    public void testValues() {
        stringMap.put("A", "1");
        stringMap.put("B", "2");
        stringMap.put("C", "3");
        stringMap.put("D", "4");
        stringMap.put("E", "5");

        Set<String> expectedValues = Set.of("1", "2", "3", "4", "5");
        Collection<String> values = stringMap.values();
        MatcherAssert.assertThat(values, hasSize(5));
        values.forEach(value -> MatcherAssert.assertThat(value, is(in(expectedValues))));
    }
}
