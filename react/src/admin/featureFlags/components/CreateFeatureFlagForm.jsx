import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import FormField from '../../../common/forms/components/FormField';
import DropdownSelector from '../../../common/forms/components/DropdownSelector';
import IconButton from '../../../common/components/IconButton';
import Request from '../../../common/js/Request';

import './CreateFeatureFlagForm.css';

const CREATE_FEATURE_FLAG_URL = '/feature-flags/api';

const FLAG_STATUS_DROPDOWN_OPTIONS = [
    {
        label: 'Enabled',
        value: 'enabled',
    },
    {
        label: 'Disabled',
        value: 'disabled',
    },
];

function setTextFromEvent(event, setText) {
    setText(event.target.value);
}

const CreateFeatureFlagForm = ({
    onSuccessfulSubmit,
}) => {
    const [flagName, setFlagName] = useState(null);
    const [flagStatus, setFlagStatus] = useState(null);
    const [userScope, setUserScope] = useState(null);
    const [userId, setUserId] = useState(null);
    const validateFlagNameField = () => true; // TODO check if the name is available
    const submitForm = useCallback(() => {
        const body = {
            name: flagName,
            status: flagStatus,
            userScope,
            userId,
        };
        Request.to(CREATE_FEATURE_FLAG_URL)
            .withAuthentication()
            .withBody(body)
            .post()
            .then(() => {
                onSuccessfulSubmit();
            });
    });

    const userIdField = userScope === 'INDIVIDUAL' && (
        <FormField
            className="user-id-field"
            label="User ID"
            isRequired={true}
            name="userId"
            text={userId}
            onChange={event => setTextFromEvent(event, setUserId)}
        />
    );

    return (
        <div className="CreateFeatureFlagForm">
            <div className="fields">
                <FormField
                    className="flag-name-field"
                    label="Flag name"
                    isRequired={true}
                    isValid={validateFlagNameField}
                    name="flagName"
                    text={flagName}
                    onChange={event => setTextFromEvent(event, setFlagName)}
                />
                <DropdownSelector
                    className="flag-status-selector"
                    options={FLAG_STATUS_DROPDOWN_OPTIONS}
                    onSelect={event => setTextFromEvent(event, setFlagStatus)}
                />
                <FormField
                    className="scope-field"
                    label="Scope"
                    isRequired={true}
                    name="userScope"
                    text={userScope}
                    onChange={event => setTextFromEvent(event, setUserScope)}
                />
                {userIdField}
            </div>
            <div className="buttons">
                <IconButton
                    className="submit-create-feature-flag-form-button"
                    variant="primary"
                    onClick={submitForm}
                >
                    Submit
                </IconButton>
            </div>
        </div>
    );
};

CreateFeatureFlagForm.propTypes = {
    onSuccessfulSubmit: PropTypes.func,
};

CreateFeatureFlagForm.defaultProps = {
    onSuccessfulSubmit: () => {},
};

export default CreateFeatureFlagForm;