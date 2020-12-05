import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import logger from '../../common/js/logging';

function getDefaultState() {
    return {
        username: null,
        userId: null,
        isLoaded: false,
    };
}

function getCurrentUserData() {
    return Request.to('/user-auth/api/legacy/get-current-user')
        .get({ credentials: 'include' })
        .catch((error) => {
            logger.error('Error fetching current user data', error);
            return {
                ...getDefaultState(),
                errorMessage: 'An error occurred. Please refresh or log in again',
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
                isLoaded: true,
            }));
    }
}