package com.kiwiko.jdashboard.webapp.apps.grocerylist;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListAppService;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroceryListConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public GroceryListAppService groceryListAppService() {
        return new GroceryListAppService();
    }
}
