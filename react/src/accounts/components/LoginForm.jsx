import React from 'react';
import PropTypes from 'prop-types';
import FormFields from '../../common/forms/components/FormFields';

import './LoginForm.css';

const LoginForm = ({
    username, password, actions, className, disableLoginButton,
}) => {
    const formFields = [
        {
            className: 'field-username',
            name: 'username',
            label: 'Username',
            text: username,
            isRequired: true,
            onChange: actions.setUsername,
            isValid: Boolean(username),
            autoComplete: 'on',
        },
        {
            className: 'field-password',
            name: 'password',
            label: 'Password',
            text: password,
            type: 'password',
            isRequired: true,
            onChange: actions.setPassword,
            isValid: Boolean(password),
            autoComplete: 'on',
        },
    ];
    const submitButtonProps = {
        className: 'button-login',
        variant: 'primary',
        fontAwesomeClassName: 'fas fa-sign-in-alt',
//         disabled={disableLoginButton}
        onClick: actions.logIn,
        children: 'Log in',
    };

    return (
        <FormFields
            fields={formFields}
            submitButtonProps={submitButtonProps}
        />
    );
};

LoginForm.propTypes = {
    username: PropTypes.string,
    password: PropTypes.string,
    actions: PropTypes.shape({
        setUsername: PropTypes.func.isRequired,
        setPassword: PropTypes.func.isRequired,
        logIn: PropTypes.func.isRequired,
    }).isRequired,
    className: PropTypes.string,
    disableLoginButton: PropTypes.bool,
};

LoginForm.defaultProps = {
    username: null,
    password: null,
    className: null,
    disableLoginButton: false,
};

export default LoginForm;