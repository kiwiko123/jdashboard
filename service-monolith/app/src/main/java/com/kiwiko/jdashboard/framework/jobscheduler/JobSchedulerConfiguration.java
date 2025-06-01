package com.kiwiko.jdashboard.framework.jobscheduler;

import com.kiwiko.jdashboard.framework.jobscheduler.api.JobScheduler;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.JobSchedulerImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PUBLIC)
@ComponentScan("com.kiwiko.jdashboard.framework.jobscheduler")
public class JobSchedulerConfiguration {

    @Bean
    public JobScheduler jobScheduler() {
        return new JobSchedulerImpl();
    }
}
