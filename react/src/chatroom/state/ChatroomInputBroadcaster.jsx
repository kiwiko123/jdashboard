import { get } from 'lodash';
import Broadcaster from '../../state/DefaultBroadcaster';
import Request from '../../common/js/Request';

const SEND_MESSAGE_URL = '/messages/api/send';

export default class ChatroomInputBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({
            messageDraft: '',
            selectedInboxItem: null,
        });
        this.registerMethod(this.onInputTextChange);
        this.registerMethod(this.sendMessage);
        this.registerMethod(this.clearSentMessage);
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'ChatroomInboxBroadcaster') {
            this.setState({
                selectedInboxItem: state.selectedItem,
            });
        }
    }

    getReRenderMillis() {
        return 25;
    }

    onInputTextChange(text) {
        this.setState({ messageDraft: text });
    }

    sendMessage({ recipientUserId } = {}) {
        const recipient = recipientUserId || get(this.state.selectedInboxItem, ['users', '0', 'userId']);
        const payload = {
            message: this.state.messageDraft,
            messageType: 1, // CHATROOM
            recipientUserId: recipient,
        };

        Request.to(SEND_MESSAGE_URL)
            .withBody(payload)
            .withAuthentication()
            .post()
            .then((data) => {
                this.setState({
                    sentMessage: data,
                    messageDraft: null,
                });
            });
    }

    clearSentMessage() {
        this.setState({ sentMessage: null });
    }
}