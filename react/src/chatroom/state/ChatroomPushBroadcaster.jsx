import PushServiceBroadcaster from '../../tools/state/PushServiceBroadcaster';
import logger from '../../common/js/logging';

export default class ChatroomPushBroadcaster extends PushServiceBroadcaster {

    constructor() {
        super({ serviceId: 'chatroom' });
    }

    onPushReceived(data) {
        const payload = JSON.parse(data);
        logger.debug(`Client received push notification from server: ${Object.entries(payload)}`);
    }
}