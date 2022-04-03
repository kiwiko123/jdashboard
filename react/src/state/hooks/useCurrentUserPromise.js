import { throttle } from 'lodash';
import Request from 'tools/http/Request';

const MAX_REQUEST_MS = 2000;

const getCurrentUserData = throttle(() => {
    return Request.to('/user-auth/public-api/users/current')
        .authenticated()
        .get();
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