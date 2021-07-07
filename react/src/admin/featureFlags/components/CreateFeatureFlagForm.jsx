import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import InputFormField from 'common/forms/components/core/InputFormField';
import DropdownSelectorFormField from 'common/forms/components/core/DropdownSelectorFormField';
import IconButton from 'common/components/IconButton';
import Request from 'common/js/Request';

import './CreateFeatureFlagForm.css';

function setTextFromEvent(event, setText) {
    setText(event.target.value);
}

const CreateFeatureFlagForm = ({
    fields, canSubmitForm, updateFieldValue, submitForm,
}) => {
    return (
        <div className="CreateFeatureFlagForm">
            <div className="fields">
                <InputFormField
                    className="flag-name-field"
                    label={fields.name.label}
                    name="flagName"
                    isRequired={fields.name.isRequired}
                    isValid={fields.name.isValid}
                    validate={fields.name.validate}
                    value={fields.name.value}
                    onChange={event => updateFieldValue('name', event.target.value)}
                />
                <DropdownSelectorFormField
                    className="flag-status-selector"
                    label={fields.status.label}
                    name={fields.status.name}
                    options={fields.status.options}
                    isRequired={fields.status.isRequired}
                    onChange={event => updateFieldValue('status', event.target.value)}
                />
                <DropdownSelectorFormField
                    className="user-scope-selector"
                    label={fields.userScope.label}
                    name={fields.userScope.name}
                    isRequired={fields.userScope.isRequired}
                    options={fields.userScope.options}
                    onChange={event => updateFieldValue('userScope', event.target.value)}
                />
            </div>
            <div className="buttons">
                <IconButton
                    className="submit-create-feature-flag-form-button"
                    variant="primary"
                    onClick={submitForm}
                    disabled={!canSubmitForm}
                >
                    Submit
                </IconButton>
            </div>
        </div>
    );
};

CreateFeatureFlagForm.propTypes = {
    fields: PropTypes.object.isRequired, // TODO define shape
    updateFieldValue: PropTypes.func.isRequired,
    canSubmitForm: PropTypes.bool,
    submitForm: PropTypes.func.isRequired,
};

CreateFeatureFlagForm.defaultProps = {
    canSubmitForm: false,
};

export default CreateFeatureFlagForm;