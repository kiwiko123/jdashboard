# Jdashboard Endpoint Interceptors
Spring supports interceptors, which are units of code that run before and/or after every web request processed by the application. 
This package provides an API that guarantees a deterministic ordering of interceptors to run.

## Spring Interceptors
To create an interceptor in vanilla Spring, there are four main steps:
1. Create a Java class that extends `HandlerInterceptorAdapter`.
2. Create a `@Configuration` class that implements `WebMvcConfigurer`, and implement the method `addInterceptors(InterceptorRegistry)` (leave blank for now).
3. Create a `@Bean` that wires up your interceptor.
4. In your overridden `addInterceptors` method, call `InterceptorRegistry#addInterceptor` on the argument registry for your interceptor bean.

## Jdashboard Endpoint Interceptors
Jdashboard provides a functional equivalent to Spring interceptors. To create one:
1. Create a Java class that implements `EndpointInterceptor`.
2. Create a `@Bean` that wires up your interceptor in `MiddlewareInterceptorConfiguration`.
3. `@Inject` your interceptor into `EndpointInterceptorChain`.
4. Add your interceptor to the list in `EndpointInterceptorChain#makeInterceptors`.

That's it! In `EndpointInterceptorChain#makeInterceptors`, you can control the ordering in which all interceptors run.