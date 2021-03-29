package com.kiwiko.webapp.flags.features.web;

import com.kiwiko.webapp.flags.features.api.FeatureFlagService;
import com.kiwiko.webapp.flags.features.dto.FeatureFlag;
import com.kiwiko.webapp.flags.features.web.responses.FeatureFlagListItem;
import com.kiwiko.webapp.users.data.User;

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
