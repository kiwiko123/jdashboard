package com.kiwiko.webapp.mvc.interceptors;

import com.kiwiko.webapp.mvc.interceptors.api.ContextMethodInterceptor;
import com.kiwiko.webapp.mvc.interceptors.data.MethodContext;
import com.kiwiko.webapp.mvc.interceptors.data.PostMethodContext;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class CaptureMetricsMethodInterceptor extends ContextMethodInterceptor {

    private Instant startTime;
    private @Nullable Instant endTime;

    @Override
    protected void preHandle(MethodContext context) {
        startTime = Instant.now();
    }

    @Override
    protected void postHandle(PostMethodContext context) {
        endTime = Instant.now();
        log(context);
    }

    private void log(PostMethodContext context) {
        Instant endTime = Optional.ofNullable(this.endTime)
                .orElseGet(Instant::now);
        Duration methodDuration = Duration.between(startTime, endTime);
        String message = String.format(
                "[@CaptureMetrics] %s.%s (%d ms)",
                context.getMethod().getDeclaringClass().getName(),
                context.getMethod().getName(),
                methodDuration.toMillis());

        if (context.getException().isPresent()) {
            logger.info(message, context.getException().get());
        } else {
            logger.info(message);
        }
    }
}
