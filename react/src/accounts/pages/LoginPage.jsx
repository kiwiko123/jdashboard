import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import LoginFormStateManager from '../state/LoginFormStateManager';
import LoginForm from '../components/LoginForm';

import '../styles/LoginPage.css';

const LoginPage = () => {
    const loginFormStateManager = useStateManager(() => new LoginFormStateManager());

    return (
        <DashboardPage
            className="LoginPage"
            title="Login"
            appId="login"
        >
            <ComponentStateManager
                stateManager={loginFormStateManager}
                component={LoginForm}
            />
        </DashboardPage>

    );
};

export default LoginPage;