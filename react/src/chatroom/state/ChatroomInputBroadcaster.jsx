import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';

const SEND_MESSAGE_URL = '/messages/api/send';

export default class ChatroomInputBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.fetchMessages = () => {};
        this.setState({
            messageDraft: null,
            onInputTextChange: this.onInputTextChange.bind(this),
            sendMessage: this.sendMessage.bind(this),
            recipientUserId: null,
        });
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'ChatroomBroadcaster') {
            this.setState({
                recipientUserId: get(state, ['selectedInboxItem', 'users', '0', 'userId']),
            });
            this.fetchMessages = state.fetchMessages;
        }
    }

    onInputTextChange(text) {
        this.setState({ messageDraft: text });
    }

    sendMessage() {
        const payload = {
            message: this.state.messageDraft,
            messageType: 1, // CHATROOM
            recipientUserId: this.state.recipientUserId,
        };

        Request.to(SEND_MESSAGE_URL)
            .withBody(payload)
            .withAuthentication()
            .post()
            .then(() => {
                this.fetchMessages();
                this.setState({ messageDraft: null });
            });
    }
}