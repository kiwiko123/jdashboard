import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import IconButton from '../../common/components/IconButton';

import '../styles/LoginForm.css';

function getTextFromEvent(event, callback) {
    const text = event.target.value;
    callback(text);
}

const LoginForm = ({
    username, password, actions, className, disableLoginButton,
}) => {
    const formClassName = classnames('LoginForm', className);
    return (
        <form
            className={formClassName}
            onSubmit={actions.logIn}
        >
            <div className="fields">
                <div className="field-username">
                    <label
                        className="label username"
                        htmlFor="username"
                    >
                        Username
                    </label>
                    <input
                        className="input username"
                        name="username"
                        type="text"
                        onChange={event => getTextFromEvent(event, actions.setUsername)}
                        autoComplete="on"
                    />
                </div>
                <div className="field-password">
                    <label
                        className="label password"
                        htmlFor="password"
                    >
                        Password
                    </label>
                    <input
                        className="input password"
                        name="password"
                        type="password"
                        onChange={event => getTextFromEvent(event, actions.setPassword)}
                        autoComplete="on"
                    />
                </div>
            </div>
            <div
                className="button-submit"
            >
                <IconButton
                    className="button-login"
                    variant="primary"
                    fontAwesomeClassName="fas fa-sign-in-alt"
                    disabled={disableLoginButton}
                    onClick={actions.logIn}
                    type="submit"
                >
                    Log in
                </IconButton>
            </div>
        </form>
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