import { get } from 'lodash';
import DebouncedUpdateBroadcaster from '../../state/DebouncedUpdateBroadcaster';
import Request from '../../common/js/Request';

const SEND_MESSAGE_URL = '/messages/api/send';

export default class ChatroomInputBroadcaster extends DebouncedUpdateBroadcaster {
    constructor() {
        super();
        this.fetchMessages = () => {};
        this.setState({
            messageDraft: '',
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

    getReRenderMillis() {
        return 25;
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
                this.setState({ messageDraft: '' });
            });
    }
}