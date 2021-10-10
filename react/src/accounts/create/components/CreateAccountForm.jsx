import React from 'react';
import PropTypes from 'prop-types';
import InputFormField from 'common/forms/components/core/InputFormField';
import IconButton from 'common/components/IconButton';

const CreateAccountForm = ({
    username, password, setUsername, setPassword, createUser,
}) => {
    return (
        <div className="CreateAccountForm">
            <div className="greeting">
                <h2>
                    Welcome!
                </h2>
                <span>
                    Let's get started.
                </span>
            </div>
            <div className="fields">
                <InputFormField
                    className="field-username"
                    name="username"
                    label="Username"
                    isRequired={true}
                    isValid={Boolean(username)}
                    validate={Boolean}
                    value={username}
                    onChange={setUsername}
                />
                <InputFormField
                    className="field-password"
                    name="password"
                    label="Password"
                    isRequired={true}
                    isValid={Boolean(password)}
                    validate={Boolean}
                    value={password}
                    onChange={setPassword}
                    type="password"
                />
            </div>
            <div className="buttons">
                <IconButton
                    className="create-button"
                    variant="primary"
                    onClick={createUser}
                    disabled={!(username && password)}
                >
                    Create
                </IconButton>
            </div>
        </div>
    );
};

CreateAccountForm.propTypes = {
    username: PropTypes.string,
    password: PropTypes.string,
    setUsername: PropTypes.func.isRequired,
    setPassword: PropTypes.func.isRequired,
    createUser: PropTypes.func.isRequired,
}

CreateAccountForm.defaultProps = {
    username: null,
    password: null,
};

export default CreateAccountForm;