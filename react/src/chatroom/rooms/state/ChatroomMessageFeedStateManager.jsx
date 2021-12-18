import PushServiceStateTransmitter from 'tools/pushService/state/PushServiceStateTransmitter';
import Request from 'tools/http/Request';

const PUSH_SERVICE_ID_PREFIX = 'chatroomMessageFeed';

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
            .authenticated()
            .get()
            .then((response) => {
                this.setState({
                    messages: response.messages,
                });
            });
    }
}