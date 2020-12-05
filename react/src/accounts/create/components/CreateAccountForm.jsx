import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import { isNil } from 'lodash';
import FormFields from '../../../common/forms/components/FormFields';

function useInputOnChange(setText) {
    return useCallback((event) => {
        setText(event.target.value);
    }, [setText]);
}

function isBlank(text) {
    return !isNil(text) && text.trim().length === 0;
}

const CreateAccountForm = ({
    username, password, setUsername, setPassword, createUser,
}) => {
    const setUsernameText = useInputOnChange(setUsername);
    const setPasswordText = useInputOnChange(setPassword);
    const formFields = [
        {
            className: 'field-username',
            name: 'username',
            label: 'Username',
            text: username,
            isRequired: true,
            onChange: setUsernameText,
            isValid: !isBlank(username),
        },
        {
            className: 'field-password',
            name: 'password',
            label: 'Password',
            text: password,
            type: 'password',
            isRequired: true,
            onChange: setPasswordText,
            isValid: !isBlank(password),
        },
    ];
    const submitButtonProps = {
        children: 'Submit',
        variant: 'primary',
        onClick: createUser,
    };

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
            <FormFields
                fields={formFields}
                submitButtonProps={submitButtonProps}
            />
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