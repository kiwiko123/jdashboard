package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.VersionRecord;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedRequest;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GetGroceryListFeedResponse;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryListFeedItem;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.QueryGroceryListsInput;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroceryListFeedLoader {

    @Inject private GroceryListService groceryListService;
    @Inject private TableRecordVersionClient tableRecordVersionClient;
    @Inject private Logger logger;

    public GetGroceryListFeedResponse getGroceryListFeed(GetGroceryListFeedRequest request) {
        Objects.requireNonNull(request.getUserId(), "User ID is required to get grocery list feed");

        QueryGroceryListsInput input = new QueryGroceryListsInput();
        input.setUserIds(Collections.singleton(request.getUserId()));
        input.setIsRemoved(false);

        List<GroceryList> groceryLists = groceryListService.query(input);

        Map<Long, Instant> lastUpdatedDatesByGroceryListId = getLastUpdatedRecordsByGroceryListId(groceryLists);

        List<GroceryListFeedItem> feedItems = new ArrayList<>();

        for (GroceryList groceryList : groceryLists) {
            Instant lastUpdatedDate = lastUpdatedDatesByGroceryListId.getOrDefault(groceryList.getId(), groceryList.getCreatedDate());

            GroceryListFeedItem feedItem = new GroceryListFeedItem();
            feedItem.setGroceryList(groceryList);
            feedItem.setItemCount(0); // TODO
            feedItem.setLastUpdatedDate(lastUpdatedDate);

            feedItems.add(feedItem);
        }

        GetGroceryListFeedResponse response = new GetGroceryListFeedResponse();
        response.setFeedItems(feedItems);
        return response;
    }

    private Map<Long, Instant> getLastUpdatedRecordsByGroceryListId(Collection<GroceryList> groceryLists) {
        Set<VersionRecord> versionRecords = groceryLists.stream()
                .map(GroceryList::getId)
                .map(id -> new VersionRecord("grocery_lists", id))
                .collect(Collectors.toSet());

        GetLastUpdatedInput input = new GetLastUpdatedInput();
        input.setVersionRecords(versionRecords);

        ClientResponse<GetLastUpdatedOutput> response = tableRecordVersionClient.getLastUpdated(input);
        if (!response.getStatus().isSuccessful()) {
            return Collections.emptyMap();
        }

        return response.getPayload().getLastUpdatedVersions().stream()
                .collect(Collectors.toMap(TableRecordVersion::getRecordId, TableRecordVersion::getCreatedDate));
    }
}
