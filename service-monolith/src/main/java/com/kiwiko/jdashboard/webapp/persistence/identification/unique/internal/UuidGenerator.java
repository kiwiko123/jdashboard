package com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal;

import com.kiwiko.jdashboard.library.lang.random.RandomUtil;

import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

public class UuidGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    @Inject private RandomUtil randomUtil;

    public String generate() {
        return UUID.randomUUID().toString();
    }

    public String generateCustomFromTimestamp() {
        return Long.toString(Instant.now().toEpochMilli()).chars()
                .mapToObj(Character::toString)
                .map(digit -> {
                    int length = randomUtil.rollDice(2) + 4;
                    return generateCustomFromTimestampSection(digit, length);
                })
                .collect(Collectors.joining("-"));
    }

    private String generateCustomFromTimestampSection(String suffix, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int alphabetIndex = randomUtil.rollDice(ALPHABET.length());
            String character = Character.toString(ALPHABET.charAt(alphabetIndex));
            result.append(randomUtil.flipCoin() ? character : character.toUpperCase());
        }
        return result.append(suffix).toString();
    }
}
