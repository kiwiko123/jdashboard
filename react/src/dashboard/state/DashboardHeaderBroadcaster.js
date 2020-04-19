import { isNil } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import { LOG_OUT_URL } from '../../accounts/js/urls';
import { logger } from '../../common/js/logs';

export default class DashboardHeaderBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.logOut = this.logOut.bind(this);
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                username: state.username,
                isLoggedIn: isNil(state.username) ? null : Boolean(state.username),
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

        new Request(LOG_OUT_URL).post({ credentials: 'include' })
            .then(() => window.location.replace('/home'));
    }
}