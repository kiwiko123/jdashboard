import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import { LOG_OUT_URL } from '../../accounts/js/urls';
import logger from '../../common/js/logging';

export default class DashboardHeaderBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.logOut = this.logOut.bind(this);
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                username: state.username,
                isLoggedIn: state.isLoaded ? Boolean(state.username) : null,
                logOut: this.logOut,
            });
        }
    }

    logOut() {
        if (!this.state.isLoggedIn) {
            logger.error('Can\'t log out; no one is logged in. Refreshing the page.');
            window.location.reload();
            return;
        }

        Request.to(LOG_OUT_URL).post({ credentials: 'include' })
            .then(() => window.location.replace('/home'));
    }
}