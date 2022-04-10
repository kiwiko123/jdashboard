import { get, orderBy } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import logger from 'tools/monitoring/logging';

export default class GroceryListFeedStateManager extends StateManager {
    constructor() {
        super();
        this.addState({
            feedItems: [],
        });
        this.refreshFeed();
    }

    refreshFeed() {
        Request.to('/grocery-list/app-api/lists/feed')
            .authenticated()
            .get()
            .then((response) => {
                let feedItems = get(response, 'feedItems', []).map(item => this._transformFeedItem(item));
                feedItems = orderBy(feedItems, ['groceryList.createdDate'], ['desc']);
                this.setState({
                    feedItems,
                });
            });
    }

    receiveCreateGroceryListFormStateManager(state, metadata) {
        switch (metadata) {
            case 'refresh':
                this.refreshFeed();
                break;
        }
    }

    _transformFeedItem(feedItem) {
        const displayDate = new Date(feedItem.groceryList.lastUpdatedDate).toDateString();

        feedItem.groceryList.displayDate = displayDate;
        feedItem.hoverActions = [
            {
                type: 'edit',
                onClick: this._editGroceryList.bind(this),
            },
            {
                type: 'remove',
                onClick: this._removeGroceryList.bind(this),
            },
        ];

        return feedItem;
    }

    _editGroceryList(groceryListId) {
        this.sendState('GroceryListModalStateManager', { groceryListId }, 'editGroceryList');
    }

    _removeGroceryList(groceryListId) {
        Request.to(`/grocery-list/app-api/lists/${groceryListId}`)
            .authenticated()
            .delete()
            .then(() => {
                this.refreshFeed();
            });
    }
}