package com.kiwiko.webapp.apps.games.pazaak.api;

import com.kiwiko.webapp.apps.games.pazaak.api.interfaces.PazaakGameCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PazaakConfiguration.class)
public class PazaakConfiguration {

    @Bean
    public PazaakGameCreator pazaakGameCreator() {
        return new PazaakGameCreator();
    }
}
