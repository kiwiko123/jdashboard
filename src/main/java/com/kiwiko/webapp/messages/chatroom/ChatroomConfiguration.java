package com.kiwiko.webapp.messages.chatroom;

import com.kiwiko.webapp.messages.chatroom.impl.ChatroomMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration {

    @Bean
    public ChatroomMessageService chatroomMessageService() {
        return new ChatroomMessageService();
    }
}
