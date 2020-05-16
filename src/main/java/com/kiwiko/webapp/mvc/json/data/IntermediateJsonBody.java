package com.kiwiko.webapp.mvc.json.data;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class IntermediateJsonBody {

    private final Map<String, Object> body;

    public IntermediateJsonBody(Map<String, Object> body) {
        this.body = Collections.unmodifiableMap(body);
    }

    /**
     * @return an unmodifiable {@link Map} representing the JSON object.
     */
    public Map<String, Object> asMap() {
        return body;
    }

    /**
     * Shorthand for {@code asMap().get(key)}.
     *
     * @param key a key in the JSON object.
     * @return the value associated with key, if present.
     */
    public Optional<Object> getValue(String key) {
        return Optional.ofNullable(body.get(key));
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), body.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().isAssignableFrom(other.getClass())) {
            return false;
        }

        IntermediateJsonBody otherJsonBody = (IntermediateJsonBody) other;
        return Objects.equals(body, otherJsonBody.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
