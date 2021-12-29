import React from 'react';
import PropTypes from 'prop-types';
import InputFormField from 'common/forms/components/core/InputFormField';
import DropdownSelectorFormField from 'common/forms/components/core/DropdownSelectorFormField';
import IconButton from 'common/components/IconButton';

import './FeatureFlagForm.css';

const FeatureFlagForm = ({
    fields, canSubmitForm, updateFieldValue, submitForm,
}) => {
    const userIdField = fields.userScope.value === 'individual' && (
        <InputFormField
            className="user-id-field"
            label={fields.userId.label}
            name="userId"
            isRequired={fields.userId.isRequired}
            isValid={fields.userId.isValid}
            validate={fields.userId.validate}
            value={fields.userId.value}
            onChange={event => updateFieldValue('userId', event.target.value)}
        />
    );

    return (
        <div className="FeatureFlagForm">
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
                    value={fields.status.value}
                    options={fields.status.options}
                    isRequired={fields.status.isRequired}
                    onChange={event => updateFieldValue('status', event.target.value)}
                />
                <InputFormField
                    className="flag-value-field"
                    label={fields.value.label}
                    name="flagValue"
                    isRequired={fields.value.isRequired}
                    isValid={fields.value.isValid}
                    value={fields.value.value}
                    onChange={event => updateFieldValue('value', event.target.value)}
                />
                <DropdownSelectorFormField
                    className="user-scope-selector"
                    label={fields.userScope.label}
                    name={fields.userScope.name}
                    value={fields.userScope.value}
                    isRequired={fields.userScope.isRequired}
                    options={fields.userScope.options}
                    onChange={event => updateFieldValue('userScope', event.target.value)}
                />
                {userIdField}
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

FeatureFlagForm.propTypes = {
    fields: PropTypes.object.isRequired, // TODO define shape
    updateFieldValue: PropTypes.func.isRequired,
    canSubmitForm: PropTypes.bool,
    submitForm: PropTypes.func.isRequired,
};

FeatureFlagForm.defaultProps = {
    canSubmitForm: false,
};

export default FeatureFlagForm;