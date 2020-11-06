import { get, sortBy } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';

const GET_MESSAGES_URL = '/messages/api/get/thread';

export default class ChatroomBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({
            messages: [],
            selectedInboxItem: null,
        });
        this.registerMethod(this.selectInboxItem);
        this.registerMethod(this.fetchMessages);

        this._continueFetching = false;
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                currentUserId: state.userId,
            });
        }
    }

    fetchMessages() {
        const requestParameters = {
            senderUserId: this.state.currentUserId,
            recipientUserId: this.state.recipientUserId,
        };
        Request.to(GET_MESSAGES_URL)
            .withRequestParameters(requestParameters)
            .withAuthentication()
            .get()
            .then((data) => {
                this.setState({ messages: this._formatMessages(data) });
                if (this._continueFetching) {
                    this.delayMessageFetch();
                }
            });
    }

    selectInboxItem(item) {
        this._continueFetching = false;
        this.setState({ selectedInboxItem: item });
        const recipientUserId = get(item, ['users', '0', 'userId']);
        this.setState({ recipientUserId });
        this.delayMessageFetch();
    }

    delayMessageFetch() {
        this._continueFetching = true;
        setTimeout(() => this.fetchMessages(), 2500);
    }

    _formatMessages(messages) {
        const formattedMessages = messages.map(message => ({
            id: message.id,
            message: message.message,
            messageStatus: message.messageStatus,
            direction: message.senderUserId === this.state.currentUserId ? 'outbound' : 'inbound',
            senderName: message.senderUserId === this.state.currentUserId ? 'Sender' : 'Recipient', // TODO
            sentDate: message.sentDate,
        }));
        return sortBy(formattedMessages, ['sentDate']);
    }
}