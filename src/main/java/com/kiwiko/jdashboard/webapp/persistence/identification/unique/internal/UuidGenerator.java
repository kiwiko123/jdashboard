package com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal;

import com.kiwiko.jdashboard.library.lang.random.RandomUtil;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagService;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Objects;

public class UuidGenerator {
    private static final int UUID_SECTION_COUNT = 5;
    private static final int UUID_SECTION_LENGTH = 10;
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();

    @Inject private RandomUtil randomUtil;
    @Inject private FeatureFlagService featureFlagService;
    @Inject private Logger logger;

    public String generate() {
        String timestampId = Instant.now().toString();
        timestampId = Integer.toString(Objects.hash(timestampId));
        final int uuidLength = getUuidLength();

        StringBuilder uuid = new StringBuilder(String.format("U%s", timestampId));

        while (uuid.length() < uuidLength) {
            String token = generateSection(UUID_SECTION_LENGTH);
            uuid.append(String.format("-%s", token));
        }

        return uuid.substring(0, uuidLength);
    }

    private String generateSection(int length) {
        StringBuilder section = new StringBuilder();
        int alphabetLength = ALPHABET.length();

        for (int i = 0; i < length; ++i) {
            char character = ALPHABET.charAt(randomUtil.rollDice(alphabetLength));
            section.append(character);
        }

        return section.toString();
    }

    private int getUuidLength() {
        String flagValue = featureFlagService.getByName("jdashboard-uuid-service-length")
                .filter(flag -> FeatureFlagStatus.ENABLED.getId().equals(flag.getStatus()))
                .filter(flag -> FeatureFlagUserScope.PUBLIC.getId().equals(flag.getUserScope()))
                .map(FeatureFlag::getValue)
                .orElse(null);

        if (flagValue == null) {
            logger.warn("No feature flag value found for jdashboard-uuid-service-length");
            return 64;
        }

        try {
            return Integer.parseInt(flagValue);
        } catch (NumberFormatException e) {
            logger.error(String.format("Malformed flag value %s", flagValue), e);
            return 64;
        }
    }
}
