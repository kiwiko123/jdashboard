import React from 'react';
import UserAuthRedirectPage from './UserAuthRedirectPage';

const LogInRedirectPage = () => (
    <UserAuthRedirectPage
        className="LogInRedirectPage"
        text="Logging in..."
        redirectWaitSeconds={1}
    />
);

export default LogInRedirectPage;