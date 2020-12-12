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
                this.fetchMessages({ recipientUserId });
            }
        }
    }

    fetchMessages({ recipientUserId }) {
        const payload = {
            senderUserId: this.state.currentUserId,
            recipientUserId,
        };
        Request.to(GET_MESSAGES_URL)
            .withAuthentication()
            .withRequestParameters(payload)
            .get()
            .then((data) => {
                this.setState({
                    messages: data,
                });
            });
    }

    _confirmMessage(message) {
        const url = `/messages/api/${message.id}/confirm`;
        Request.to(url)
            .withAuthentication()
            .put();
    }
}