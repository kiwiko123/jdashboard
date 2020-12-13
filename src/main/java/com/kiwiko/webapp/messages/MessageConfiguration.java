package com.kiwiko.webapp.messages;

import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import com.kiwiko.webapp.messages.internal.helpers.MessageServiceHelper;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.internal.UserEntityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {

    @Bean
    public MessageEntityDAO messageEntityDAO() {
        return new MessageEntityDAO();
    }

    @Bean
    public MessageEntityFieldMapper messageEntityFieldMapper() {
        return new MessageEntityFieldMapper();
    }

    @Bean
    public UserService userService() {
        return new UserEntityService();
    }

    @Bean
    public MessageServiceHelper messageServiceHelper() {
        return new MessageServiceHelper();
    }
}
