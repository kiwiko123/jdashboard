package com.kiwiko.jdashboard.webapp.apps.games.pazaak;

import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameCreator;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameHandler;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameLoader;
import com.kiwiko.jdashboard.webapp.apps.games.state.GameStateConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.JsonConfiguration;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PazaakConfiguration.class)
public class PazaakConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({GameStateConfiguration.class, JsonConfiguration.class, GsonJsonConfiguration.class})
    public PazaakGameCreator pazaakGameCreator() {
        return new PazaakGameCreator();
    }

    @Bean
    @ConfiguredBy(GameStateConfiguration.class)
    public PazaakGameLoader pazaakGameLoader() {
        return new PazaakGameLoader();
    }

    @Bean
    @ConfiguredBy({GameStateConfiguration.class, JsonConfiguration.class, GsonJsonConfiguration.class})
    public PazaakGameHandler pazaakGameHandler() {
        return new PazaakGameHandler();
    }
}
