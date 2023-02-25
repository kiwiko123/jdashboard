import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import InputFormField from 'common/forms/components/core/InputFormField';
import DropdownSelectorFormField from 'common/forms/components/core/DropdownSelectorFormField';
import StandardButton from 'ui/StandardButton';

import './SimpleForm.css';

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
    fields, isFormValid, actions, className, theme,
}) => {
    const fieldComponents = getFieldComponents(fields);
    const divClassName = classnames('SimpleForm', className, {
        [`theme-${theme}`]: theme,
    });

    return (
        <div className={divClassName}>
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
    className: PropTypes.string,
    theme: PropTypes.oneOf(['light', 'dark']),
};

SimpleForm.defaultProps = {
    fields: {},
    isFormValid: false,
    className: null,
    theme: null,
};

export default SimpleForm;