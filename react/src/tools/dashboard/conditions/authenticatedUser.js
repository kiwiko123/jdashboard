import getCurrentUser from 'tools/users/util/getCurrentUser';

export default {
    name: 'authenticatedUser',
    condition: () => getCurrentUser().then(Boolean),
};