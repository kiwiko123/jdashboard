import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import MessageHelper from './MessageHelper';

const GET_MESSAGES_URL = '/messages/api/get/thread';

export default class ChatroomBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({
            messages: [],
            currentUserId: null,
        });
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                currentUserId: state.id,
            });
        } else if (broadcasterId === 'ChatroomInboxBroadcaster') {
            const recipientUserId = get(state, ['selectedItem', 'users', '0', 'userId']);
            if (recipientUserId) {
                this.fetchMessages({
                    recipientUserId,
                    merge: false,
                });
            }
        } else if (broadcasterId === 'ChatroomInputBroadcaster') {
            if (state.sentMessage) {
                this.fetchMessages({
                    recipientUserId: state.sentMessage.recipientUserId,
                    minimumSentDate: state.sentMessage.sentDate,
                    merge: true,
                }).then(state.clearSentMessage);
            }
        } else if (broadcasterId === 'ChatroomPushBroadcaster') {
            if (state.newMessage) {
                // TODO consider new messages from outside the currently selected thread
                this.fetchMessages({
                    recipientUserId: state.newMessage.senderUserId,
                    minimumSentDate: state.newMessage.sentDate,
                    merge: true,
                }).then(state.clearNewMessage);
            }
        }
    }

    fetchMessages({ recipientUserId, minimumSentDate, merge = false }) {
        const payload = {
            senderUserId: this.state.currentUserId,
            recipientUserId,
            minimumSentDate,
        };
        return Request.to(GET_MESSAGES_URL)
            .withAuthentication()
            .withRequestParameters(payload)
            .get()
            .then((data) => {
                let messages = merge
                    ? this.state.messages.concat(data)
                    : data;
                this.setState({ messages });
            });
    }

    _confirmMessage(message) {
        const url = `/messages/api/${message.id}/confirm`;
        Request.to(url)
            .withAuthentication()
            .put();
    }
}