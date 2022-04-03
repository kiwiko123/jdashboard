import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import logger from 'common/js/logging';
import { useCurrentUserPromise } from 'state/hooks';
import { goTo } from 'common/js/urltools';

export default class DashboardHeaderStateManager extends StateManager {
    constructor() {
        super();

        this.setState({
            isMenuSlideOverExpanded: false,
            userData: {
                isLoggedIn: false,
            },
        });
        this.registerMethod(this.toggleMenuSlideOver);

        useCurrentUserPromise().then((user) => {
            if (!user) {
                return;
            }
            this.setState({
                userData: {
                    username: user.username,
                    isLoggedIn: true,
                    logOut: this.logOut.bind(this),
                },
            });
        });
    }

    logOut() {
        if (!this.state.userData.isLoggedIn) {
            logger.error('Can\'t log out; no one is logged in. Refreshing the page.');
            window.location.reload();
            return;
        }

        Request.to('/user-auth/api/users/current/logout')
            .authenticated()
            .post()
            .then(() => {
                goTo('/home');
            });
    }

    toggleMenuSlideOver() {
        this.setState({ isMenuSlideOverExpanded: !this.state.isMenuSlideOverExpanded });
    }
}