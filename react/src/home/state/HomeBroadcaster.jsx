import Broadcaster from '../../state/Broadcaster';

export default class HomeBroadcaster extends Broadcaster {

    receive(state, broadcasterId) {
        if (broadcasterId === 'DashboardNotificationsBroadcaster') {
            this.setState(state);
        }
    }
}