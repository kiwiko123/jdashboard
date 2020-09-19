import React from 'react';
import PropTypes from 'prop-types';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import LoginFormBroadcaster from '../state/LoginFormBroadcaster';
import LoginForm from '../components/LoginForm';

import '../styles/LoginPage.css';

const LoginPage = ({ history }) => {
    const loginFormBroadcaster = new LoginFormBroadcaster();

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

LoginPage.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
};

export default LoginPage;