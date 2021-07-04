package com.kiwiko.webapp.messages;

import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
import com.kiwiko.webapp.messages.internal.helpers.MessageServiceHelper;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.webapp.users.UserConfiguration;
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
    @ConfiguredBy(UserConfiguration.class)
    public MessageServiceHelper messageServiceHelper() {
        return new MessageServiceHelper();
    }
}
