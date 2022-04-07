import getCurrentUser from 'tools/users/util/getCurrentUser';

export default function authenticatedUser() {
    return {
        name: 'authenticatedUser',
        condition: getCurrentUser().then(Boolean),
    };
}