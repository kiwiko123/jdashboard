package com.kiwiko.jdashboard.library.strings;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class StringSubstitutorTest {
    private StringSubstitutor stringSubstitutor = new StringSubstitutor();

    @Before
    public void setUp() {
        stringSubstitutor = new StringSubstitutor();
    }

    @Test
    public void testMakePlaceholderPositions_simple() {
        String message = "Y {} x {}";
        List<StringPlaceholderPosition> result = stringSubstitutor.makePlaceholderPositions(message, "{}");
        MatcherAssert.assertThat(result, hasSize(2));
        MatcherAssert.assertThat(result, contains(new StringPlaceholderPosition(2, 4), new StringPlaceholderPosition(7, 9)));
    }

    @Test
    public void testMakePlaceholderPositions_long() {
        String message = "{} is a {} message with {}{}.{}";
        List<StringPlaceholderPosition> result = stringSubstitutor.makePlaceholderPositions(message, "{}");
        MatcherAssert.assertThat(result, hasSize(5));
        MatcherAssert.assertThat(
                result,
                contains(
                        new StringPlaceholderPosition(0, 2),
                        new StringPlaceholderPosition(8, 10),
                        new StringPlaceholderPosition(24, 26),
                        new StringPlaceholderPosition(26, 28),
                        new StringPlaceholderPosition(29, 31)));
    }

    @Test
    public void testMakePlaceholderPositions_nestedSimple() {
        String message = "Nest {{}}";
        List<StringPlaceholderPosition> result = stringSubstitutor.makePlaceholderPositions(message, "{}");
        MatcherAssert.assertThat(result, hasSize(1));
        MatcherAssert.assertThat(result, contains(new StringPlaceholderPosition(6, 8)));
    }

    @Test
    public void testSubstitute_short() {
        String message = "Y {} x {}";
        String result = stringSubstitutor.substitute(message, "{}", "test1", "test2");
        Assert.assertEquals("Y test1 x test2", result);
    }

    @Test
    public void testSubstitute_long() {
        String message = "{} is a {} message with {}{}.{}";
        String result = stringSubstitutor.substitute(message, "{}", "test1", "test2", "test3", "test4", "test5");
        Assert.assertEquals("test1 is a test2 message with test3test4.test5", result);
    }
}
