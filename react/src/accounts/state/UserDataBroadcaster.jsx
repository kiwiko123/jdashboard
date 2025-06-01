import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import logger from '../../common/js/logging';

function getDefaultState() {
    return {
        username: null,
        permissions: [],
        id: null,
        userId: null, // alias for id; deprecated
        isLoaded: false,
    };
}

function getCurrentUserData() {
    return Request.to('/user-auth/api/users/current')
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

        getCurrentUserData()
            .then(user => this.setState({
                username: user.username,
                permissions: new Set(user.permissions),
                id: user.id,
                userId: user.id, // deprecated
                isLoaded: true,
            }))
    }
}