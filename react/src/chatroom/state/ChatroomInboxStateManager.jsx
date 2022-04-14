import { get } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';

const GET_FEED_URL = '/chatroom/api/inbox/feed';

export default class ChatroomInboxStateManager extends StateManager {

    constructor() {
        super();

        this.setState({
            inboxItems: [],
        });

        this.refreshFeed();
    }

    refreshFeed() {
        Request.to(GET_FEED_URL)
            .authenticated()
            .get()
            .then((response) => {
                this.setState({ inboxItems: get(response, 'inboxItems', []) });
            });
    }
}