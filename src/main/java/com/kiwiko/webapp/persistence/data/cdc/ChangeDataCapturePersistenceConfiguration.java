package com.kiwiko.webapp.persistence.data.cdc;

import com.kiwiko.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ChangeDataCapturePersistenceConfiguration.class)
public class ChangeDataCapturePersistenceConfiguration {

    @Bean
    public DataChangeCapturer dataChangeCapturer() {
        return new DataChangeCapturer();
    }
}
