package com.kiwiko.jdashboard.library.strings;

import java.util.Objects;

class StringPlaceholderPosition {
    private final int startPosition;
    private final int endPosition;

    public StringPlaceholderPosition(int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringPlaceholderPosition that = (StringPlaceholderPosition) o;
        return startPosition == that.startPosition && endPosition == that.endPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition);
    }

    @Override
    public String toString() {
        return "StringPlaceholderPosition{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                '}';
    }
}
