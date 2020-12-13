import { get } from 'lodash';
import PushServiceBroadcaster from '../../tools/state/PushServiceBroadcaster';
import logger from '../../common/js/logging';
import Request from '../../common/js/Request';

export default class ChatroomPushBroadcaster extends PushServiceBroadcaster {

    constructor() {
        super({ serviceId: 'chatroom' });
        this.registerMethod(this.clearNewMessage);
    }

    onPushReceived(data) {
        const payload = JSON.parse(data);
        logger.debug(`Client received push notification from server: ${Object.entries(payload)}`);

        const messageId = get(payload, 'messageId');
        const url = `/chatroom/api/message/${messageId}`;
        Request.to(url)
            .withAuthentication()
            .get()
            .then((data) => {
                this.setState({
                    newMessage: data,
                });
            });
    }

    clearNewMessage() {
        this.setState({ newMessage: null });
    }
}