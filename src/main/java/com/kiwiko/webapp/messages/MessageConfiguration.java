package com.kiwiko.webapp.messages;

import com.kiwiko.webapp.messages.api.MessageService;
import com.kiwiko.webapp.messages.internal.MessageEntityFieldMapper;
import com.kiwiko.webapp.messages.internal.dataAccess.MessageEntityDAO;
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
}
