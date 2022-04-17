package com.kiwiko.jdashboard.webapp.apps.grocerylist;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListAppService;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListCoreConfiguration;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListFeedLoader;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListPermissionChecker;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroceryListConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(GroceryListCoreConfiguration.class)
    public GroceryListAppService groceryListAppService() {
        return new GroceryListAppService();
    }

    @Bean
    @ConfiguredBy(GroceryListCoreConfiguration.class)
    public GroceryListFeedLoader groceryListFeedLoader() {
        return new GroceryListFeedLoader();
    }

    @Bean
    public GroceryListPermissionChecker groceryListPermissionChecker() {
        return new GroceryListPermissionChecker();
    }
}
