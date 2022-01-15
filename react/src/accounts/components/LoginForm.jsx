import React from 'react';
import PropTypes from 'prop-types';
import InputFormField from 'common/forms/components/core/InputFormField';
import IconButton from 'common/components/IconButton';

import './LoginForm.css';

const LoginForm = ({
    fields, actions, canSubmitForm,
}) => {
    return (
        <div className="LoginForm">
            <div className="fields">
                <InputFormField
                    {...fields.username}
                    className="username"
                    onSubmit={actions.submitForm}
                />
                <InputFormField
                    {...fields.password}
                    onSubmit={actions.submitForm}
                />
            </div>
            <div className="buttons">
                <IconButton
                    className="button-login"
                    variant="primary"
                    fontAwesomeClassName="fas fa-sign-in-alt"
                    disabled={!canSubmitForm}
                    onClick={actions.submitForm}
                >
                    Log in
                </IconButton>
            </div>
        </div>
    );
};

LoginForm.propTypes = {
    fields: PropTypes.shape({
        username: PropTypes.object.isRequired,
        password: PropTypes.object.isRequired,
    }).isRequired,
    actions: PropTypes.shape({
        submitForm: PropTypes.func.isRequired,
    }).isRequired,
    canSubmitForm: PropTypes.bool,
};

LoginForm.defaultProps = {
    canSubmitForm: false,
};

export default LoginForm;