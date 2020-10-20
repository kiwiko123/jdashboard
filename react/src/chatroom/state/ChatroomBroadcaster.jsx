import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';

const GET_MESSAGES_URL = '/messages/api/get/thread';

export default class ChatroomBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({
            messageDraft: null,
            onInputTextChange: this.onInputTextChange,
            messages: [],
        });
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                currentUserId: state.userId,
            });
            if (state.userId) {
                this.fetchMessages();
            }
        }
    }

    fetchMessages() {
        const requestParameters = {
            senderUserId: this.state.currentUserId,
            recipientUserId: 3, // TODO don't hardcode
        };
        Request.to(GET_MESSAGES_URL)
            .withRequestParameters(requestParameters)
            .withAuthentication()
            .get()
            .then((data) => {
                this.setState({ messages: data });
            });
    }

    onInputTextChange(text) {
        this.setState({ messageDraft: text });
    }
}