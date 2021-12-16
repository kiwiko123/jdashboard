package com.kiwiko.jdashboard.webapp.apps.chatroom;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.ChatroomInboxServiceImpl;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.ChatroomRoomServiceImpl;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageRoomUserService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.ChatroomMessageService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomUserEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageMapper;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomMapper;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomUserMapper;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public ChatroomInboxService chatroomInboxService() {
        return new ChatroomInboxServiceImpl();
    }

    @Bean
    public ChatroomRoomService chatroomRoomService() {
        return new ChatroomRoomServiceImpl();
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
    public ChatroomMessageService chatroomMessageService() {
        return new ChatroomMessageService();
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
