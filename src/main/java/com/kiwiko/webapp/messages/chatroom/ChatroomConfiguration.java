package com.kiwiko.webapp.messages.chatroom;

import com.kiwiko.webapp.messages.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.messages.chatroom.internal.ChatroomMessageEntityDAO;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration {

    public ChatroomMessageEntityDAO chatroomMessageEntityDAO() {
        return new ChatroomMessageEntityDAO();
    }

    public ChatroomMessageService chatroomMessageService() {
        return new ChatroomMessageService();
    }
}
