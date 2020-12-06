import { isEmpty, throttle } from 'lodash';
import Request from '../../common/js/Request';
import { GET_CURRENT_USER_URL } from '../../accounts/js/urls';

const MAX_REQUEST_MS = 2000;

const getCurrentUserData = throttle(() => {
    return Request.to(GET_CURRENT_USER_URL)
        .withAuthentication()
        .get()
        .then((data) => {
            if (isEmpty(data)) {
                return null;
            }
            return {
                id: data.id,
                username: data.username,
            };
        });
}, MAX_REQUEST_MS);

/**
 * Fetch the current user's User data.
 * If a user is logged in, the promise resolves to an object containing their User data.
 * Otherwise, resolve to null.
 *
 * @return a Promise containing the current user's data, or null if no user is logged in
 */
export default function() {
    return getCurrentUserData();
}