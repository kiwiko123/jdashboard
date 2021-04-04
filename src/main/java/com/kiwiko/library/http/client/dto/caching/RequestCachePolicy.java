package com.kiwiko.library.http.client.dto.caching;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Objects;

public class RequestCachePolicy {
    public static RequestCachePolicy none() {
        return new RequestCachePolicy();
    }

    private @Nullable Duration duration;

    @Nullable
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(@Nullable Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestCachePolicy policy = (RequestCachePolicy) o;
        return Objects.equals(duration, policy.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }

    @Override
    public String toString() {
        return "RequestCachePolicy{" +
                "duration=" + duration +
                '}';
    }

    public static class Builder {
        private final RequestCachePolicy policy;

        public Builder() {
            policy = new RequestCachePolicy();
        }

        public Builder setDuration(@Nullable Duration duration) {
            policy.setDuration(duration);
            return this;
        }

        public RequestCachePolicy build() {
            return policy;
        }
    }
}
