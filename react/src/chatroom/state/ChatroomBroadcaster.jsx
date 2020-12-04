import { findLast, get, last, sortBy, uniqBy } from 'lodash';
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

        const mostRecentMessage = last(this.state.messages);
        if (mostRecentMessage) {
            requestParameters.minimumSentDate = mostRecentMessage.sentDate;
        }

        return Request.to(GET_MESSAGES_URL)
            .withRequestParameters(requestParameters)
            .withAuthentication()
            .get()
            .then((data) => {
                const newMessages = this._processMessages(data);
                const mergedMessages = uniqBy([...this.state.messages, ...newMessages], message => message.id);
                if (this.state.messages.length !== mergedMessages.length) {
                    const mostRecentInboundMessage = findLast(
                        mergedMessages,
                        message => MessageHelper.isInboundMessage(this.state.currentUserId, message),
                    );
                    if (mostRecentInboundMessage && mostRecentInboundMessage.messageStatus !== 'DELIVERED') {
                        this._confirmMessage(mostRecentInboundMessage);
                    }
                    const mostRecentOutboundMessage = findLast(
                        mergedMessages,
                        message => MessageHelper.isOutboundMessage(this.state.currentUserId, message),
                    );
                    this.setState({ messages: mergedMessages });
                }
            });
    }

    selectInboxItem(item) {
        const recipientUserId = get(item, ['users', '0', 'userId']);
        this.setState({
            selectedInboxItem: item,
            recipientUserId,
            messages: [],
        });
    }

    _processMessages(messages) {
        const orderedMessages = sortBy(messages, ['sentDate']);
        return orderedMessages.map(message => ({
            id: message.id,
            message: message.message,
            direction: MessageHelper.isOutboundMessage(this.state.currentUserId, message) ? 'outbound' : 'inbound',
            senderName: MessageHelper.isOutboundMessage(this.state.currentUserId, message) ? 'Sender' : 'Recipient', // TODO
            sentDate: message.sentDate,
//             messageStatus: message.messageStatus,
        }));
    }

    _confirmMessage(message) {
        const url = `/messages/api/${message.id}/confirm`;
        Request.to(url)
            .withAuthentication()
            .put();
    }
}