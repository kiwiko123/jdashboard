package com.kiwiko.jdashboard.webapp.framework.interceptors.api;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.interceptors.data.PostMethodContext;
import com.kiwiko.jdashboard.webapp.framework.interceptors.data.MethodContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Inject;

public abstract class ContextMethodInterceptor implements MethodInterceptor {

    @Inject protected Logger logger;

    /**
     * Override this to execute any logic prior to the annotated method's execution.
     *
     * @param context
     */
    protected void preHandle(MethodContext context) { }

    /**
     * Override this to execute any logic immediately following the intercepted method's execution.
     * {@link #postHandle(PostMethodContext)} will still be invoked even if the intercepted method threw an {@link Exception};
     * the exception will be re-thrown after this.
     *
     * @param context
     */
    protected void postHandle(PostMethodContext context) { }

    protected boolean canInvoke() {
        return true;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodContext context = new MethodContext(invocation);
        PostMethodContext postContext = new PostMethodContext(invocation, null, null);
        preHandle(context);

        if (!canInvoke()) {
            return null;
        }

        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Exception e) {
            logger.error("Intercepted method threw exception", e);
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
