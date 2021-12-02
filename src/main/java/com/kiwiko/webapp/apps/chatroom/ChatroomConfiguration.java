package com.kiwiko.webapp.apps.chatroom;

import com.kiwiko.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.webapp.apps.chatroom.internal.ChatroomInboxServiceImpl;
import com.kiwiko.webapp.apps.chatroom.internal.core.ChatroomMessageRoomService;
import com.kiwiko.webapp.apps.chatroom.internal.core.ChatroomMessageRoomUserService;
import com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomUserEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageMapper;
import com.kiwiko.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomMapper;
import com.kiwiko.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomUserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration {

    @Bean
    public ChatroomInboxService chatroomInboxService() {
        return new ChatroomInboxServiceImpl();
    }

    @Bean
    public ChatroomMessageEntityDataFetcher chatroomMessageEntityDataFetcher() {
        return new ChatroomMessageEntityDataFetcher();
    }

    @Bean
    public ChatroomMessageRoomEntityDataFetcher chatroomMessageRoomEntityDataFetcher() {
        return new ChatroomMessageRoomEntityDataFetcher();
    }

    @Bean
    public ChatroomMessageRoomUserEntityDataFetcher chatroomMessageRoomUserEntityDataFetcher() {
        return new ChatroomMessageRoomUserEntityDataFetcher();
    }

    @Bean
    public ChatroomMessageMapper chatroomMessageMapper() {
        return new ChatroomMessageMapper();
    }

    @Bean
    public ChatroomMessageRoomMapper chatroomMessageRoomMapper() {
        return new ChatroomMessageRoomMapper();
    }

    @Bean
    public ChatroomMessageRoomUserMapper chatroomMessageRoomUserMapper() {
        return new ChatroomMessageRoomUserMapper();
    }

    @Bean
    public ChatroomMessageRoomService chatroomMessageRoomService() {
        return new ChatroomMessageRoomService();
    }

    @Bean
    public ChatroomMessageRoomUserService chatroomMessageRoomUserService() {
        return new ChatroomMessageRoomUserService();
    }
}
