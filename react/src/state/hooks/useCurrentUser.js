import { useCallback, useEffect, useState } from 'react';
import useCurrentUserPromise from './useCurrentUserPromise';

/**
 * Like useCurrentUserPromise, but returns an actual value rather than a Promise.
 * Note that this will always return null on the first invocation while it asynchronously fetches
 * the current user's data.
 * After the request finishes, a re-render will trigger and useCurrentUser will return the updated data.
 *
 * @return the current user's data
 * @see useCurrentUserPromise
 */
export default function() {
    const [currentUserData, setCurrentUserData] = useState(null);
    useEffect(() => {
        useCurrentUserPromise().then(setCurrentUserData);
    }, []);

    return currentUserData;
}