import Broadcaster from '../../state/Broadcaster';
import { RequestService, extractResponse, handleErrors } from '../../common/js/requests';
import Request from '../../common/js/Request';
import { LOG_IN_URL } from '../js/urls';
import DashboardAlertActions from '../../dashboard/state/actions/DashboardAlertActions';

const SERVER_URL = 'http://localhost:8080';

export default class LoginFormBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.requestService = new RequestService(SERVER_URL)
            .withResponseExtractor(extractResponse)
            .withErrorHandler(response => handleErrors(response, errors => this.setState({ errors })));

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
            disableLoginButton: !(text && this.state.emailAddress),
        });
    }

    logIn() {
        this.setState({ disableLoginButton: true });
        const payload = {
            username: this.state.username,
            password: this.state.password,
        };

        new Request(LOG_IN_URL)
            .withBody(payload)
            .post({ credentials: 'include' })
            .then((response) => {
                window.location.replace('/home');
            })
            .catch(error => {
                DashboardAlertActions.addAlert({
                    bannerType: 'danger',
                    message: 'There was an error signing in. Please try again.',
                });
            });
//         this.requestService
//             .post(LOG_IN_URL, payload)
//             .then((response) => {
//                 window.location.replace('/home');
//             })
//             .catch(error => {
//                 DashboardAlertActions.addAlert({
//                     bannerType: 'danger',
//                     message: 'There was an error signing in. Please try again.',
//                 });
//             });
    }
}