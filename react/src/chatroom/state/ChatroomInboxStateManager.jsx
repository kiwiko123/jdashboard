import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

const GET_FEED_URL = '/chatroom/api/inbox/feed';

export default class ChatroomInboxStateManager extends StateTransmitter {

    constructor() {
        super();

        this.setState({
            inboxItems: [],
        });

        this.refreshFeed();
    }

    refreshFeed() {
        Request.to(GET_FEED_URL)
            .withAuthentication()
            .get()
            .then((response) => {
                this.setState({ inboxItems: get(response, 'inboxItems', []) });
            });
    }
}