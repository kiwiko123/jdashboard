import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { isEmpty } from 'lodash';
import InputFormField from 'common/forms/components/core/InputFormField';
import DropdownSelectorFormField from 'common/forms/components/core/DropdownSelectorFormField';
import StandardButton from 'ui/StandardButton';
import Banner, { BANNER_TYPES } from 'common/components/Banner';

import './SimpleForm.css';

function getFormErrors(errors) {
    if (isEmpty(errors)) {
        return null;
    }
    const banners = errors.map(error => (
        <Banner
            key={error.message}
            type={BANNER_TYPES.danger}
        >
            {error.message}
        </Banner>
    ));
    return (
        <div className="form-errors">
            {banners}
        </div>
    );
}

function getFieldComponent(type) {
    switch (type) {
        case 'dropdownSelector':
            return DropdownSelectorFormField;
        case 'input':
        default:
            return InputFormField;
    }
}

function getFieldComponents(fields) {
    return Object.values(fields)
       .map((field) => {
           const FieldComponent = getFieldComponent(field.type);
           return (
               <FieldComponent
                   key={field.name}
                   {...field}
               />
           );
       });
}

const SimpleForm = ({
    fields, isFormValid, errors, actions, className, theme,
}) => {
    const formErrors = getFormErrors(errors);
    const fieldComponents = getFieldComponents(fields);
    const divClassName = classnames('SimpleForm', className, {
        [`theme-${theme}`]: theme,
    });

    return (
        <div className={divClassName}>
            {formErrors}
            <div className="fields">
                {fieldComponents}
            </div>
            <div className="form-buttons">
                <StandardButton
                    className="reset-form-button"
                    variant="warning"
                    fontAwesomeClassName="fas fa-undo"
                    onClick={actions.clearForm}
                >
                    Reset
                </StandardButton>
                <StandardButton
                    className="submit-form-button"
                    variant="primary"
                    fontAwesomeClassName="fas fa-paper-plane"
                    onClick={actions.submitForm}
                    disabled={!isFormValid}
                >
                    Submit
                </StandardButton>
            </div>
        </div>
    );
};

SimpleForm.propTypes = {
    fields: PropTypes.objectOf(PropTypes.shape({
        name: PropTypes.string.isRequired,
        type: PropTypes.oneOf(['input', 'dropdownSelector']),
        label: PropTypes.string.isRequired,
        value: PropTypes.any,
        isRequired: PropTypes.bool,
        validate: PropTypes.func,
        isValid: PropTypes.bool,
        onChange: PropTypes.func,
    })),
    isFormValid: PropTypes.bool,
    actions: PropTypes.shape({
        submitForm: PropTypes.func.isRequired,
        clearForm: PropTypes.func.isRequired,
    }).isRequired,
    errors: PropTypes.arrayOf(PropTypes.shape({
        message: PropTypes.string.isRequired,
    })),
    className: PropTypes.string,
    theme: PropTypes.oneOf(['light', 'dark']),
};

SimpleForm.defaultProps = {
    fields: {},
    isFormValid: false,
    errors: null,
    className: null,
    theme: null,
};

export default SimpleForm;