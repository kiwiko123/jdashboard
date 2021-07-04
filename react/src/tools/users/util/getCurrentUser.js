import Request from 'common/js/Request';
import logger from 'common/js/logging';

export default function() {
    return Request.to('/user-auth/api/users/current')
        .withAuthentication()
        .get()
        .catch((error) => {
            logger.error('Error fetching the currently logged-in user');
        });
}