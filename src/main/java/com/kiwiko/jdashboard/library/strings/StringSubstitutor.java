package com.kiwiko.jdashboard.library.strings;

import com.google.common.annotations.VisibleForTesting;
import com.kiwiko.jdashboard.library.strings.exceptions.StringSubstitutionException;

import java.util.LinkedList;

public class StringSubstitutor {

    /**
     * Given a string with placeholder templates, substitute those placeholders with the provided arguments.
     *
     * Example:
     * {@code substitute("This {} a {} {}", "{}", "is", "nice", "message") -> "This is a nice message"}.
     *
     * The number of arguments must match the number of occurrences of placeholder in the subject.
     *
     * @param subject the string template, containing placeholders
     * @param placeholder the placeholder in the subject to substitute
     * @param args the arguments that will sequentially be substituted into the subject
     * @return the string with all placeholders substituted with the input arguments
     * @throws StringSubstitutionException
     */
    public String substitute(String subject, String placeholder, Object... args) throws StringSubstitutionException {
        if (args.length == 0) {
            return subject;
        }

        LinkedList<StringPlaceholderPosition> placeholderPositions = makePlaceholderPositions(subject, placeholder);
        if (placeholderPositions.size() != args.length) {
            throw new StringSubstitutionException(
                    String.format(
                            "Number of placeholders must match number of arguments; found %d placeholders but expected %d in \"%s\"",
                            placeholderPositions.size(),
                            args.length,
                            subject));
        }

        return buildSubstitutedString(subject, placeholderPositions, args);
    }

    @VisibleForTesting
    LinkedList<StringPlaceholderPosition> makePlaceholderPositions(String subject, String placeholder) {
        LinkedList<StringPlaceholderPosition> placeholderPositions = new LinkedList<>();
        int currentPosition = 0;
        int subjectLength = subject.length();
        int placeholderLength = placeholder.length();
        char placeholderStartingCharacter = placeholder.charAt(0);

        while ((currentPosition + placeholderLength) <= subjectLength) {
            if (subject.charAt(currentPosition) == placeholderStartingCharacter) {
                int endPosition = currentPosition + placeholderLength;
                String block = subject.substring(currentPosition, endPosition);
                if (block.equals(placeholder)) {
                    StringPlaceholderPosition placeholderPosition = new StringPlaceholderPosition(currentPosition, endPosition);
                    placeholderPositions.add(placeholderPosition);
                    currentPosition += placeholderLength;
                    continue;
                }
            }

            ++currentPosition;
        }

        return placeholderPositions;
    }

    private String buildSubstitutedString(
            String subject,
            LinkedList<StringPlaceholderPosition> placeholderPositions,
            Object[] args) {
        int subjectLength = subject.length();
        int currentPosition = 0;
        StringPlaceholderPosition currentPlaceholderPosition = placeholderPositions.removeFirst(); // placeholderPositions guaranteed to be non-empty
        StringBuilder stringBuilder = new StringBuilder();

        while (currentPosition < subjectLength) {
            char currentCharacter = subject.charAt(currentPosition);
            if (currentPlaceholderPosition == null) {
                String remainingString = subject.substring(currentPosition);
                stringBuilder.append(remainingString);
                break;
            }

            if (currentPosition == currentPlaceholderPosition.getStartPosition()) {
                Object substitute = args[args.length - placeholderPositions.size() - 1];
                stringBuilder.append(substitute);
                currentPosition = currentPlaceholderPosition.getEndPosition();
                currentPlaceholderPosition = placeholderPositions.isEmpty() ? null : placeholderPositions.removeFirst();
                continue;
            }

            stringBuilder.append(currentCharacter);
            ++currentPosition;
        }

        return stringBuilder.toString();
    }
}
