package com.kiwiko.mvc.interceptors.api;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.interceptors.data.PostMethodContext;
import com.kiwiko.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import com.kiwiko.mvc.interceptors.data.MethodContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract class WebMethodInterceptor implements MethodInterceptor {

    @InjectManually private LogService logService;

    protected void preHandle(MethodContext context) { }

    protected void postHandle(PostMethodContext context) { }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodContext context = new MethodContext(invocation);
        PostMethodContext postContext = new PostMethodContext(invocation, null, null);
        preHandle(context);

        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Exception e) {
            logService.error("Intercepted method threw exception", e);
            postContext = new PostMethodContext(invocation, null, e);
        }

        postContext = new PostMethodContext(invocation, result, postContext.getException().orElse(null));
        postHandle(postContext);

        if (postContext.getException().isPresent()) {
            throw postContext.getException().get();
        }

        return result;
    }
}
