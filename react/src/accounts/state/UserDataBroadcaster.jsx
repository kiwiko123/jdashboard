import Broadcaster from '../../state/Broadcaster';
import { GET_CURRENT_USER_URL } from '../js/urls';
import Request from '../../common/js/Request';
import { logger } from '../../common/js/logs';

function getDefaultState() {
    return {
        username: null,
        userId: null,
    };
}

function getCurrentUserData() {
    return new Request(GET_CURRENT_USER_URL).get({ credentials: 'include' })
        .catch((error) => {
            logger.error('Error fetching current user data', error);
            const defaultState = getDefaultState();
            return {
                ...defaultState,
                errorMessage: 'An error occurred. Please refresh or log in again.',
            };
        });
}

export default class UserDataBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.setState(getDefaultState());
        getCurrentUserData()
            .then(user => this.setState({
                username: user.username,
                userId: user.id,
            }));
    }
}