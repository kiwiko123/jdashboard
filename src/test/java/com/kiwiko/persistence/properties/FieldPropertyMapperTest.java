package com.kiwiko.persistence.properties;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FieldPropertyMapperTest {

    private FieldPropertyMapper<CarEntity, CarDTO> carPropertyMapper;

    @Before
    public void setUp() {
        carPropertyMapper = new FieldPropertyMapper<>();
    }

    @Test
    public void testPublicFieldIsCopied() {
        String make = "Honda";
        CarEntity carEntity = new CarEntity(make, "red", 4);
        CarDTO carDTO = new CarDTO();

        CarDTO copied = carPropertyMapper.toTargetType(carEntity, carDTO);
        MatcherAssert.assertThat("Expected \"make\" to have been copied", copied.getMake(), is(notNullValue()));
        MatcherAssert.assertThat("Expected \"make\" to have been copied", copied.getMake(), is(equalTo(make)));
    }

    @Test
    public void testProtectedFieldIsCopied() {
        String color = "red";
        CarEntity carEntity = new CarEntity("Honda", color, 4);
        CarDTO carDTO = new CarDTO();

        CarDTO copied = carPropertyMapper.toTargetType(carEntity, carDTO);
        MatcherAssert.assertThat("Expected \"color\" to have been copied", copied.getColor(), is(notNullValue()));
        MatcherAssert.assertThat("Expected \"color\" to have been copied", copied.getColor(), is(equalTo(color)));
    }

    @Test
    public void testPrivateFieldIsCopied() {
        int numberOfWheels = 4;
        CarEntity carEntity = new CarEntity("Honda", "red", numberOfWheels);
        CarDTO carDTO = new CarDTO();

        CarDTO copied = carPropertyMapper.toTargetType(carEntity, carDTO);
        MatcherAssert.assertThat("Expected \"numberOfWheels\" to have been copied", copied.getNumberOfWheels(), is(equalTo(numberOfWheels)));
    }

    private static class CarEntity {
        public String make;
        protected String color;
        private int numberOfWheels;

        public CarEntity(String make, String color, int numberOfWheels) {
            this.make = make;
            this.color = color;
            this.numberOfWheels = numberOfWheels;
        }

        public String getMake() {
            return make;
        }

        public String getColor() {
            return color;
        }

        public int getNumberOfWheels() {
            return numberOfWheels;
        }
    }

    private static class CarDTO {
        private String make;
        private String color;
        private int numberOfWheels;

        public String getMake() {
            return make;
        }

        public String getColor() {
            return color;
        }

        public int getNumberOfWheels() {
            return numberOfWheels;
        }
    }
}
