package com.kiwiko.webapp.apps.chatroom;

import com.kiwiko.webapp.apps.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.apps.chatroom.internal.ChatroomPushReceiver;
import com.kiwiko.webapp.apps.chatroom.internal.ChatroomPushService;
import com.kiwiko.webapp.apps.chatroom.web.helpers.ChatroomResponseHelper;
import com.kiwiko.webapp.push.api.PushServiceConfigurationCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class ChatroomConfiguration {

    @Inject private PushServiceConfigurationCreator pushServiceConfigurationCreator;

    @Bean
    public ChatroomMessageService chatroomMessageService() {
        return new ChatroomMessageService();
    }

    @Bean
    public ChatroomPushService chatroomPushService() {
        return new ChatroomPushService();
    }

    @Bean
    public ChatroomPushReceiver chatroomPushReceiver() {
        return pushServiceConfigurationCreator.create(ChatroomPushReceiver::new);
    }

    @Bean
    public ChatroomResponseHelper chatroomResponseHelper() {
        return new ChatroomResponseHelper();
    }
}
