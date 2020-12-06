import React from 'react';
import PropTypes from 'prop-types';
import FormFields from '../../../common/forms/components/FormFields';

function isBlank(text) {
    return !text;
}

const CreateAccountForm = ({
    username, password, setUsername, setPassword, createUser,
}) => {
    const formFields = [
        {
            className: 'field-username',
            name: 'username',
            label: 'Username',
            text: username,
            isRequired: true,
            onChange: setUsername,
            isValid: !isBlank(username),
            autoComplete: 'on',
        },
        {
            className: 'field-password',
            name: 'password',
            label: 'Password',
            text: password,
            type: 'password',
            isRequired: true,
            onChange: setPassword,
            isValid: !isBlank(password),
            autoComplete: 'on',
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