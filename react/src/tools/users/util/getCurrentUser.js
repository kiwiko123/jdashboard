import { throttle } from 'lodash';
import Request from 'tools/http/Request';

const MAX_REQUEST_MS = 2000;
const getCurrentUserData = throttle(() => {
    return Request.to('/user-auth/public-api/users/current')
        .authenticated()
        .get();
}, MAX_REQUEST_MS);

export default getCurrentUserData;