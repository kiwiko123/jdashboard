package com.kiwiko.jdashboard.library.lang.random;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();

    /**
     * @return true or false, randomly
     */
    public boolean flipCoin() {
        return RANDOM.nextBoolean();
    }

    /**
     *
     * @param sides the number of sides on the dice (exclusive)
     * @return a random number in the bounded range [0, sides)
     */
    public int rollDice(int sides) {
        return RANDOM.nextInt(sides);
    }

    /**
     * Return true given some odds. For example, if odds is 10, there is a 1/10 chance of returning true.
     *
     * @param odds the odds of returning true
     * @return true or false given the odds
     */
    public boolean chance(int odds) {
        return rollDice(odds) == 0;
    }
}
