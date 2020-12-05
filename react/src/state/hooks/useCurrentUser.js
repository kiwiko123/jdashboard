import { useCallback, useEffect, useState } from 'react';
import { get, isEmpty, throttle } from 'lodash';
import Request from '../../common/js/Request';
import { GET_CURRENT_USER_URL } from '../../accounts/js/urls';

const MAX_REQUEST_MS = 10000;

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

export default function() {
    const [currentUserData, setCurrentUserData] = useState(null);

    useEffect(() => {
        getCurrentUserData().then(setCurrentUserData);
    }, []);

    return currentUserData;
}