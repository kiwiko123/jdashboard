package com.kiwiko.jdashboard.langutils.types;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class TypesHelperTest {
    private TypesHelper typesHelper;

    @BeforeEach
    public void setUp() {
        typesHelper = new TypesHelper();
    }

    @Test
    public void testGetCorrespondingType_primitive() {
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(boolean.class), is(Boolean.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(int.class), is(Integer.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(long.class), is(Long.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(double.class), is(Double.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(float.class), is(Float.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(short.class), is(Short.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(byte.class), is(Byte.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(char.class), is(Character.class));
    }

    @Test
    public void testGetCorrespondingType_object() {
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Boolean.class), is(boolean.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Integer.class), is(int.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Long.class), is(long.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Double.class), is(double.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Float.class), is(float.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Short.class), is(short.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Byte.class), is(byte.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(Character.class), is(char.class));
    }

    @Test
    public void testGetCorrespondingType_unmappedTypeReturnsItself() {
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(String.class), is(String.class));
        MatcherAssert.assertThat(typesHelper.getCorrespondingType(TestType.class), is(TestType.class));
    }

    private static class TestType { }
}
