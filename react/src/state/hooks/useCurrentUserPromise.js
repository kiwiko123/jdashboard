import getCurrentUser from 'tools/users/util/getCurrentUser';

/**
 * Fetch the current user's User data.
 * If a user is logged in, the promise resolves to an object containing their User data.
 * Otherwise, resolve to null.
 *
 * @return a Promise containing the current user's data, or null if no user is logged in
 */
export default function() {
    return getCurrentUser();
}