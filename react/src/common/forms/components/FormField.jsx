import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import FormInput from './FormInput';

import './FormField.css';

const FormField = ({
    label, isRequired, isValid, className,

    // FormInput props
    text, type, name, onSubmit, onChange, onBlur, autoComplete,
}) => {
    const labelText = isRequired ? `${label}*` : label;
    const divClassName = classnames('FormField', className);
    const formInputClassName = classnames({
        invalid: isValid === false, // undefined/null are considered valid
    })
    const inputProps = { text, type, name, onSubmit, onChange, onBlur, autoComplete };

    return (
        <div className={divClassName}>
            <label className="label">
                {labelText}
            </label>
            <FormInput
                {...inputProps}
                className={formInputClassName}
            />
        </div>
    );
};

FormField.propTypes = {
    ...FormInput.props,
    label: PropTypes.string.isRequired,
    isRequired: PropTypes.bool,
    isValid: PropTypes.bool,
};

FormField.defaultProps = {
    ...FormInput.defaultProps,
    isRequired: false,
    isValid: true,
};

export default FormField;