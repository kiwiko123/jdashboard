import React from 'react';
import PropTypes from 'prop-types';
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
            <ComponentStateManager broadcaster={loginFormBroadcaster}>
                <LoginForm />
            </ComponentStateManager>
        </DashboardPage>

    );
};

export default LoginPage;