package com.kiwiko.jdashboard.webapp.apps.grocerylist;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListService;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroceryListConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public GroceryListEntityDataAccessObject groceryListEntityDataAccessObject() {
        return new GroceryListEntityDataAccessObject();
    }

    @Bean
    public GroceryListEntityMapper groceryListEntityMapper() {
        return new GroceryListEntityMapper();
    }

    @Bean
    public GroceryListService groceryListService() {
        return new GroceryListService();
    }
}
