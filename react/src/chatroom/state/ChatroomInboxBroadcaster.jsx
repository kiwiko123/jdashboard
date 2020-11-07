import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';

export default class ChatroomInboxBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.fetchMessages = () => {};
        this.setState({
            inboxItems: [],
            selectInboxItem: () => {},
        });
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'ChatroomBroadcaster') {
            this.setState({
                currentUserId: state.currentUserId,
                selectInboxItem: state.selectInboxItem,
            });
            this.fetchMessages = state.fetchMessages;
            if (state.currentUserId) {
                this.fetchPreviews(state.currentUserId);
            }
        }
    }

    fetchPreviews(userId) {
        const previewUrl = `/messages/api/get/users/${userId}/previews`;
        Request.to(previewUrl)
            .withAuthentication()
            .get()
            .then(data => this.setState({ inboxItems: data }));
    }
}