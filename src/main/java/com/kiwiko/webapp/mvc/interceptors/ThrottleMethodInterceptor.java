package com.kiwiko.webapp.mvc.interceptors;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.webapp.mvc.performance.api.annotations.Throttle;
import com.kiwiko.webapp.mvc.performance.api.errors.ThrottleException;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.interceptors.api.ContextMethodInterceptor;
import com.kiwiko.webapp.mvc.interceptors.data.MethodContext;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.time.Duration;

public class ThrottleMethodInterceptor extends ContextMethodInterceptor {

    private boolean canInvokeMethod;

    @Inject
    private ObjectCache objectCache;

    @Inject
    private LogService logService;

    @Override
    protected void preHandle(MethodContext context) {
        Method method = context.getMethod();
        String cacheId = getId(method);
        boolean shouldThrottle = objectCache.get(cacheId).isPresent();
        Throttle throttle = method.getAnnotation(Throttle.class);

        if (shouldThrottle) {
            canInvokeMethod = false;
            RuntimeException exception = new ThrottleException(String.format("%s is being throttled", method.getName()));
            if (throttle.throwException()) {
                throw exception;
            } else {
                logService.warn("@Throttle", exception);
            }
            return;
        } else {
            canInvokeMethod = true;
        }

        Duration cacheDuration = Duration.of(throttle.maxWait(), throttle.timeUnit());
        objectCache.cache(cacheId, true, cacheDuration);
    }

    @Override
    protected boolean canInvoke() {
        return canInvokeMethod;
    }

    private String getId(Method method) {
        return String.format("ThrottleMethodInterceptorCacheId_%s", method.getName());
    }
}
