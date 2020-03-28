package com.kiwiko.mvc.interceptors;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.interceptors.api.WebMethodInterceptor;
import com.kiwiko.mvc.interceptors.data.MethodContext;
import com.kiwiko.mvc.interceptors.data.PostMethodContext;
import com.kiwiko.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class CaptureMetricsMethodInterceptor extends WebMethodInterceptor {

    @InjectManually private LogService logService;
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
            logService.info(message, context.getException().get());
        } else {
            logService.info(message);
        }
    }
}
