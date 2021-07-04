import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useBroadcaster } from '../../state/hooks/broadcasterHooks';
import LoginFormBroadcaster from '../state/LoginFormBroadcaster';
import LoginForm from '../components/LoginForm';

import '../styles/LoginPage.css';

const LoginPage = () => {
    const loginFormBroadcaster = useBroadcaster(LoginFormBroadcaster);

    return (
        <DashboardPage
            className="LoginPage"
            title="Login"
            appId="login"
        >
            <ComponentStateManager
                broadcaster={loginFormBroadcaster}
                component={LoginForm}
                id="LoginForm"
            />
        </DashboardPage>

    );
};

export default LoginPage;