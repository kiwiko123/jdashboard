import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './FormField.css';

const FormField = ({
    label, isRequired, isValid, children, className,
}) => {
    const labelText = isRequired ? `${label}*` : label;
    const divClassName = classnames('FormField', className);
    const inputClassName = classnames('input', { invalid: isValid === false });

    return (
        <div className={divClassName}>
            <label className="label">
                {labelText}
            </label>
            <div className={inputClassName}>
                {children}
            </div>
        </div>
    );
};

FormField.propTypes = {
    children: PropTypes.node.isRequired,
    label: PropTypes.string.isRequired,
    isRequired: PropTypes.bool,
    isValid: PropTypes.bool,
    className: PropTypes.string,
};

FormField.defaultProps = {
    isRequired: false,
    isValid: true,
    className: null,
};

export default FormField;