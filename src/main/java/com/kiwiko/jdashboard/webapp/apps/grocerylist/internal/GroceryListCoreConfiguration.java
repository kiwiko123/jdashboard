package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroceryListCoreConfiguration {

    @Bean
    public GroceryListEntityDataAccessObject groceryListEntityDataAccessObject() {
        return new GroceryListEntityDataAccessObject();
    }

    @Bean
    public GroceryListEntityMapper groceryListEntityMapper() {
        return new GroceryListEntityMapper();
    }

    @Bean
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public GroceryListService groceryListService() {
        return new GroceryListService();
    }
}
