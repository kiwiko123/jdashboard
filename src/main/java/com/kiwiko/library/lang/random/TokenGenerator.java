package com.kiwiko.library.lang.random;

import java.util.Random;

public class TokenGenerator {

    private static final int TOKEN_LENGTH = 32;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-=_+[]{}|.<>/?~";
    private static final Random RANDOM = new Random();

    public String generateToken(int length) {
        int alphabetSize = ALPHABET.length();
        String result = "";

        for (int i = 0; i < length; ++i) {
            int randomIndex = RANDOM.nextInt(alphabetSize);
            String nextCharacter = Character.toString(ALPHABET.charAt(randomIndex));
            if (RANDOM.nextBoolean()) {
                nextCharacter = nextCharacter.toLowerCase();
            }
            result += nextCharacter;
        }

        return result;
    }

    public String generateToken() {
        return generateToken(TOKEN_LENGTH);
    }
}
