import PushServiceBroadcaster from '../../tools/pushService/state/PushServiceBroadcaster';
// import Broadcaster from '../../state/Broadcaster';

const SERVICE_ID = 'jdashboard-notifications';

export default class DashboardNotificationsBroadcaster extends PushServiceBroadcaster {
    constructor() {
        super({ serviceId: SERVICE_ID });
    }

    onPushReceived(data) {
        const payload = JSON.parse(data);
        super.onPushReceived(payload);
    }
}