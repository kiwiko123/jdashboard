import { get } from 'lodash';
import Request from '../../common/js/Request';
import { GET_CURRENT_USER_URL } from '../../accounts/js/urls';

function getCurrentUserData() {
    return Request.to(GET_CURRENT_USER_URL)
        .withAuthentication()
        .get()
        .then((data) => {
            if (!data) {
                return null;
            }
            return {
                id: data.id,
                username: data.username,
            };
        });
}

export default function() {
    return getCurrentUserData();
}