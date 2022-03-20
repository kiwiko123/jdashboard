package com.kiwiko.jdashboard.services.featureflags.web;

import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.services.featureflags.web.responses.FeatureFlagListItem;
import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.users.api.dto.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class FeatureFlagAPIHelper {

    @Inject private FeatureFlagService featureFlagService;

    public List<FeatureFlagListItem> getListItems() {
        Set<FeatureFlag> flags = featureFlagService.getAll();
        List<FeatureFlagListItem> listItems = new ArrayList<>();

        for (FeatureFlag flag : flags) {
            FeatureFlagListItem listItem = makeListItem(flag, null);
            listItems.add(listItem);
        }

        return listItems;
    }

    private FeatureFlagListItem makeListItem(FeatureFlag flag, User user) {
        FeatureFlagListItem listItem = new FeatureFlagListItem();
        listItem.setFeatureFlag(flag);
        listItem.setUser(user);

        return listItem;
    }
}
