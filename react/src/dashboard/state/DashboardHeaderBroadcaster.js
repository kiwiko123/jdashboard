import Broadcaster from '../../state/Broadcaster';

export default class DashboardHeaderBroadcaster extends Broadcaster {

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                username: state.username,
            });
        }
    }
}