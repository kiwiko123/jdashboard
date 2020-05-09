import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import { LOG_IN_URL } from '../js/urls';
import DashboardAlertActions from '../../dashboard/state/actions/DashboardAlertActions';

function handleLoginErrors(response) {
    const errors = get(response, 'errors', []);
    if (errors.length === 0) {
        return;
    }

    errors.forEach(message => DashboardAlertActions.addAlert({ message, bannerType: 'warning' }));
    throw new Error('Failed to log in');
}

export default class LoginFormBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.logIn = this.logIn.bind(this);

        this.setState({
            username: null,
            password: null,
            actions: {
                setUsername: this.setUsername,
                setPassword: this.setPassword,
                logIn: this.logIn,
            },
            disableLoginButton: true,
        });
    }

    setUsername(text) {
        this.setState({
            username: text,
            disableLoginButton: !(text && this.state.password),
        });
    }

    setPassword(text) {
        this.setState({
            password: text,
            disableLoginButton: !(text && this.state.username),
        });
    }

    logIn() {
        this.setState({ disableLoginButton: true });
        const payload = {
            username: this.state.username,
            password: this.state.password,
        };

        Request.to(LOG_IN_URL)
            .withErrorHandler(handleLoginErrors)
            .withBody(payload)
            .post({ credentials: 'include' })
            .then((response) => {
                window.location.href = '/home';
            })
            .catch(error => {
                DashboardAlertActions.addAlert({
                    bannerType: 'danger',
                    message: 'There was an error signing in. Please try again.',
                });
            });
    }
}