package com.kiwiko.jdashboard.webapp.apps.grocerylist;

import com.kiwiko.jdashboard.clients.tablerecordversions.TableRecordVersionClientConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListAppService;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListCoreConfiguration;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListFeedLoader;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListPermissionChecker;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroceryListConfiguration {

    @Bean
    @ConfiguredBy(GroceryListCoreConfiguration.class)
    public GroceryListAppService groceryListAppService() {
        return new GroceryListAppService();
    }

    @Bean
    @ConfiguredBy({
            GroceryListCoreConfiguration.class,
            TableRecordVersionClientConfiguration.class,
            LoggingConfiguration.class,
    })
    public GroceryListFeedLoader groceryListFeedLoader() {
        return new GroceryListFeedLoader();
    }

    @Bean
    @ConfiguredBy(GroceryListCoreConfiguration.class)
    public GroceryListPermissionChecker groceryListPermissionChecker() {
        return new GroceryListPermissionChecker();
    }
}
