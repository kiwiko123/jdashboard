package com.kiwiko.webapp.chatroom;

import com.kiwiko.webapp.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.chatroom.internal.ChatroomPushService;
import com.kiwiko.webapp.push.api.PushService;
import com.kiwiko.webapp.push.api.PushServiceConfigurationCreator;
import com.kiwiko.webapp.push.api.PushServiceRegistry;
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
        return pushServiceConfigurationCreator.create(ChatroomPushService::new);
    }
}
