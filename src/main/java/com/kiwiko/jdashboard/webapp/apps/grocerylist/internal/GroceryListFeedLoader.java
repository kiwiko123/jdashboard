package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        Map<Long, List<TableRecordVersion>> versionsByGroceryListId = getTableRecordVersionsByGroceryListId(groceryLists);
        List<GroceryListFeedItem> feedItems = new ArrayList<>();

        for (GroceryList groceryList : groceryLists) {
            Instant lastUpdatedDate = Optional.ofNullable(versionsByGroceryListId.get(groceryList.getId()))
                    .map(versions -> versions.get(versions.size() - 1))
                    .map(TableRecordVersion::getCreatedDate)
                    .orElseGet(groceryList::getCreatedDate);

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

    private Map<Long, List<TableRecordVersion>> getTableRecordVersionsByGroceryListId(Collection<GroceryList> groceryLists) {
        Set<Long> groceryListIds = groceryLists.stream()
                .map(GroceryList::getId)
                .collect(Collectors.toUnmodifiableSet());

        Map<Long, List<TableRecordVersion>> versionsByGroceryListId = new HashMap<>();
        for (Long groceryListId : groceryListIds) {
            // TODO make bulk request
            GetTableRecordVersions getVersions = new GetTableRecordVersions("grocery_lists", groceryListId);
            ClientResponse<GetTableRecordVersionOutput> response = tableRecordVersionClient.getVersions(getVersions);
            if (!response.getStatus().isSuccessful()) {
                logger.warn("Unable to fetch table record versions for grocery list {}", groceryListId);
                continue;
            }

            versionsByGroceryListId.put(groceryListId, response.getPayload().getTableRecordVersions());
        }

        return versionsByGroceryListId;
    }
}
