package com.kiwiko.webapp.apps.chatroom;

import com.kiwiko.webapp.apps.chatroom.internal.data.fetchers.ChatroomMessageEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.data.fetchers.ChatroomMessageRoomEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.data.fetchers.ChatroomMessageRoomUserEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.data.mappers.ChatroomMessageMapper;
import com.kiwiko.webapp.apps.chatroom.internal.data.mappers.ChatroomMessageRoomMapper;
import com.kiwiko.webapp.apps.chatroom.internal.data.mappers.ChatroomMessageRoomUserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration {

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
}
