import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import FormInput from './FormInput';

import './FormField.css';

const FormField = ({
    label, isRequired, isValid,

    // FormInput props
    ...props,
}) => {
    const labelText = isRequired ? `${label}*` : label;
    const divClassName = classnames('FormField', props.className);
    const formInputClassName = classnames({
        invalid: isValid === false, // undefined/null are considered valid
    })

    return (
        <div className={divClassName}>
            <label className="label">
                {labelText}
            </label>
            <FormInput
                {...props}
                className={formInputClassName}
            />
        </div>
    );
};

FormField.propTypes = {
    ...FormInput.propTypes,
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