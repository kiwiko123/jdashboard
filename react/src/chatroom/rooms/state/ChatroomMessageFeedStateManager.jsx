import { get } from 'lodash';
import PushServiceStateTransmitter from 'tools/pushService/state/PushServiceStateTransmitter';
import Request from 'common/js/Request';

const PUSH_SERVICE_ID_PREFIX = 'chatroomMessageFeed';
const GET_FEED_URL = '/chatroom/api/inbox/feed';

export default class ChatroomMessageFeedStateManager extends PushServiceStateTransmitter {

    constructor({ roomId }) {
        super(`${PUSH_SERVICE_ID_PREFIX}-${roomId}`);

        this.roomId = roomId;
        this.setState({
            messages: [],
        });

        this.refreshFeed();
    }

    refreshFeed() {
        Request.to(`/chatroom/api/room/${this.roomId}/messages`)
            .withAuthentication()
            .get()
            .then((response) => {
                this.setState({
                    messages: response.messages,
                });
            });
    }
}