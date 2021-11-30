# Push Service
Jdashboard's Push Service is a publish-subscribe message bus API.
The current implementation is a naive websocket-powered approach just to demonstrate a proof-of-concept.

At a high level, the client opens a single websocket connection with the server that lasts as long as it's alive. 
The client can register as many "push services" as it wants, but all of them will route through the single websocket. 
When a client's push service sends a message, it must include a service ID that identifies itself. 
The server then uses that service ID to determine how and where to route the message.

## API
### Server
To create a new server-side push service, do the following:
1. Create a class that implements `PushServiceSubscriber`. You'll need to implement the method `onPushReceived`, which 
   will be invoked whenever a message is received by the server.
   ```java
   public class MyPushServiceSubscriber implements PushServiceSubscriber {
        @Override
        public void onPushReceived(OnPushReceivedParameters parameters) {
            System.out.println("A push has been received!");
        }
   }
   ```
2. Create a Spring `@Configuration` class that wires up this subscriber as a bean.
   ```java
   @Configuration
   public class MyPushServiceSubscriberConfiguration {
        @Bean
        public MyPushServiceSubscriber myPushServiceSubscriber() {
             return new MyPushServiceSubscriber();
        }    
   }
   ```
3. Register the subscriber and its configuration in the `SubscribePushServicesCreator` application startup hook.
   ```java
   pushServiceSubscriberRegistry.register(new RegisterPushServiceSubscriberParameters("my-push-service", MyPushServiceSubscriber.class, MyPushServiceSubscriberConfiguration.class));
   ```
   
### Client
1. Create a state transmitter that extends `PushServiceStateTransmitter`.