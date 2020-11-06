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
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                currentUserId: state.userId,
            });
            if (state.userId) {
                this.fetchPreviews(state.userId);
            }
        } else if (broadcasterId === 'ChatroomBroadcaster') {
            this.setState({
                selectInboxItem: state.selectInboxItem,
            });
            this.fetchMessages = state.fetchMessages;
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