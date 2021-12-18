import { get } from 'lodash';
import PushServiceStateTransmitter from 'tools/pushService/state/PushServiceStateTransmitter';
import Request from 'tools/http/Request';

const PUSH_SERVICE_ID_PREFIX = 'chatroomMessageFeed';
const GET_FEED_URL = '/chatroom/api/inbox/feed';

export default class ChatroomMessageFeedStateManager extends PushServiceStateTransmitter {

    constructor({ roomUuid }) {
        super(`${PUSH_SERVICE_ID_PREFIX}-${roomUuid}`);

        this.roomUuid = roomUuid;
        this.setState({
            messages: [],
        });

        this.refreshFeed();
    }

    refreshFeed() {
        Request.to(`/chatroom/api/room/${this.roomUuid}/messages`)
            .authenticated()
            .get()
            .then((response) => {
                this.setState({
                    messages: response.messages,
                });
            });
    }
}