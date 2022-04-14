import React from 'react';
import UserAuthRedirectPage from './UserAuthRedirectPage';

const LogOutRedirectPage = () => (
    <UserAuthRedirectPage
        className="LogOutRedirectPage"
        text="Logging out..."
        redirectWaitSeconds={4}
    />
);

export default LogOutRedirectPage;