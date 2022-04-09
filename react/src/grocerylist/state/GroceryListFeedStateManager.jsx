import { get } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';

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
                this.setState({
                    feedItems: get(response, 'feedItems', []),
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
}