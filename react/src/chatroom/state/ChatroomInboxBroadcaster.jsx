import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';

export default class ChatroomInboxBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({
            inboxItems: [],
            selectedItem: null,
        });
        this.registerMethod(this.selectInboxItem);
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            if (state.id) {
                this.fetchPreviews(state.id);
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

    selectInboxItem(item) {
        this.setState({
            selectedItem: item,
        });
    }
}