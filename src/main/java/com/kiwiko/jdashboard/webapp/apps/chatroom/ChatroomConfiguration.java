package com.kiwiko.jdashboard.webapp.apps.chatroom;

import com.kiwiko.jdashboard.clients.users.UserClientConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomInboxService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomPermissionService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.ChatroomRoomService;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.ChatroomInboxServiceImpl;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.ChatroomPermissionServiceImpl;
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
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.PushServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatroomConfiguration {

    @Bean
    @ConfiguredBy({UniversalUniqueIdentifierConfiguration.class, UserClientConfiguration.class})
    public ChatroomInboxService chatroomInboxService() {
        return new ChatroomInboxServiceImpl();
    }

    @Bean
    @ConfiguredBy({PushServiceConfiguration.class, UserClientConfiguration.class, GsonJsonConfiguration.class, LoggingConfiguration.class})
    public ChatroomRoomService chatroomRoomService() {
        return new ChatroomRoomServiceImpl();
    }

    @Bean
    public ChatroomPermissionService chatroomPermissionService() {
        return new ChatroomPermissionServiceImpl();
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
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public ChatroomMessageService chatroomMessageService() {
        return new ChatroomMessageService();
    }

    @Bean
    @ConfiguredBy({UniversalUniqueIdentifierConfiguration.class, PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public ChatroomMessageRoomService chatroomMessageRoomService() {
        return new ChatroomMessageRoomService();
    }

    @Bean
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public ChatroomMessageRoomUserService chatroomMessageRoomUserService() {
        return new ChatroomMessageRoomUserService();
    }
}
