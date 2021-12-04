package com.kiwiko.jdashboard.webapp.apps.games.pazaak;

import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameCreator;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameHandler;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameLoader;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PazaakConfiguration.class)
public class PazaakConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public PazaakGameCreator pazaakGameCreator() {
        return new PazaakGameCreator();
    }

    @Bean
    public PazaakGameLoader pazaakGameLoader() {
        return new PazaakGameLoader();
    }

    @Bean
    public PazaakGameHandler pazaakGameHandler() {
        return new PazaakGameHandler();
    }
}
