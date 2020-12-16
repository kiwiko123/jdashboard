# Push Service
This package contains logic that powers websocket-based live updates, like push notifications.

## Leveraging push notifications
To leverage your own push-based logic, start by implementing the `PushService` interface.
For convenience, you may instead wish to extend `TextWebSocketPushService`, which provides some boilerplate implementation.

### Service ID
You must assign your service implementation a unique ID, by implementing the `serviceId` method.
This can simply be the name of your service/application, as long as it's unique.

### Sending push notifications
Call `pushToClient` to attempt sending a push notification to the client.
If the client cannot be found, this will throw a `ClientUnreachablePushException`.

### Receiving push notifications
`onPushReceived` will be invoked when a push from the client to the server is received.

### Spring configuration
An important step! In your Spring configuration class, use `PushServiceConfigurationCreator` to wire up the bean.
This takes care of boilerplate logic that ensures push notifications are routed to your service.

```java
import com.kiwiko.webapp.push.api.PushServiceConfigurationCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class MyPushServiceConfiguration {

    @Inject
    private PushServiceConfigurationCreator pushServiceConfigurationCreator;

    @Bean
    public MyPushService myPushService() {
        return pushServiceConfigurationCreator.create(MyPushService::new);
    }
}
```