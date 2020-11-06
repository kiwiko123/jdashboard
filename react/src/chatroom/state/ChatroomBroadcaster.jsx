import { findLast, get, sortBy } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import MessageHelper from './MessageHelper';

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
        return Request.to(GET_MESSAGES_URL)
            .withRequestParameters(requestParameters)
            .withAuthentication()
            .get()
            .then((data) => {
                this.setState({ messages: this._formatMessages(data) });
            });
    }

    selectInboxItem(item) {
        this._continueFetching = false;
        const recipientUserId = get(item, ['users', '0', 'userId']);
        this.setState({
            selectedInboxItem: item,
            recipientUserId,
        });
        this._continueFetching = true;
        this.delayMessageFetch();
    }

    delayMessageFetch() {
        if (!this._continueFetching) {
            return;
        }
        this.fetchMessages()
            .then(() => setTimeout(() => this.delayMessageFetch(), 5000));
    }

    _formatMessages(messages) {
        const orderedMessages = sortBy(messages, ['sentDate']);
        const mostRecentInboundMessage = findLast(
            orderedMessages,
            message => MessageHelper.isInboundMessage(this.state.currentUserId, message),
        );
        if (mostRecentInboundMessage && mostRecentInboundMessage.messageStatus !== 'delivered') {
            this._confirmMessage(mostRecentInboundMessage);
        }
        const mostRecentOutboundMessage = findLast(
            orderedMessages,
            message => MessageHelper.isOutboundMessage(this.state.currentUserId, message),
        );

        return orderedMessages.map(message => ({
            id: message.id,
            message: message.message,
            direction: MessageHelper.isOutboundMessage(this.state.currentUserId, message) ? 'outbound' : 'inbound',
            senderName: MessageHelper.isOutboundMessage(this.state.currentUserId, message) ? 'Sender' : 'Recipient', // TODO
            sentDate: message.sentDate,
            messageStatus: message.id === get(mostRecentOutboundMessage, 'id') ? message.messageStatus : null,
        }));
    }

    _confirmMessage(message) {
        const url = `/messages/api/${message.id}/confirm`;
        Request.to(url)
            .withAuthentication()
            .put();
    }
}