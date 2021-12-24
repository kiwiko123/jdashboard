import { get } from 'lodash';
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
    }

    receiveChatroomRoomPermissionStateManager(state, metadata) {
        if (metadata === 'canAccess') {
            this.refreshFeed();
        }
    }

    onPushReceived(data) {
        const message = JSON.parse(data);
        this._loadMessage(message.id);
    }

    receiveChatroomMessageInputStateManager(state, metadata) {
        if (metadata === 'messageSent') {
            this._loadMessage(state.chatroomMessageId);
        }
    }

    refreshFeed() {
        Request.to(`/chatroom/api/room/${this.roomId}/messages`)
            .authenticated()
            .get()
            .then((response) => {
                const messages = get(response, 'messages', []);
                this.setState({
                    messages: this._transformMessages(messages),
                });
            });
    }

    _transformMessage(message) {
        const direction = message.chatroomMessage.senderUserId === this.userId ? 'outbound' : 'inbound';
        return {
            ...message,
            direction,
//             status: direction === 'outbound' ? message.chatroomMessage.messageStatus : null,
        };
    }

    _transformMessages(messages) {
        return messages.map(message => this._transformMessage(message));
    }

    _loadMessage(chatroomMessageId) {
        Request.to(`/chatroom/api/room/message/${chatroomMessageId}`)
            .authenticated()
            .get()
            .then((response) => {
                const { messages } = this.state;
                this.setState({
                    messages: [...messages, this._transformMessage(response)],
                });
            });
    }
}