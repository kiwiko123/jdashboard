import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import { LOG_OUT_URL } from '../../accounts/js/urls';
import logger from '../../common/js/logging';

export default class DashboardHeaderBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.logOut = this.logOut.bind(this);

        this.setState({
            isMenuSlideOverExpanded: false,
            toggleMenuSlideOver: this.toggleMenuSlideOver.bind(this),
            userData: {
                isLoggedIn: false,
            },
        });
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({
                userData: {
                    username: state.username,
                    permissions: state.permissions,
                    isLoggedIn: state.isLoaded ? Boolean(state.username) : null,
                    logOut: this.logOut,
                },
            });
        }
    }

    logOut() {
        if (!this.state.userData.isLoggedIn) {
            logger.error('Can\'t log out; no one is logged in. Refreshing the page.');
            window.location.reload();
            return;
        }

        Request.to(LOG_OUT_URL).post({ credentials: 'include' })
            .then(() => window.location.replace('/home'));
    }

    toggleMenuSlideOver() {
        this.setState({ isMenuSlideOverExpanded: !this.state.isMenuSlideOverExpanded });
    }
}