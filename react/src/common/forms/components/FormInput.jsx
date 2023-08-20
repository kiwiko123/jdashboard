import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const FormInput = ({
    text, type, name, className, onSubmit, onChange, onBlur, autoComplete, placeholder, ...props,
}) => {
    const divClassName = classnames('FormInput', className);
    // TODO useEventSubmit
    const onKeyDown = useCallback((event) => {
        if (onSubmit && event.keyCode === 13) { // Enter/Return
            onSubmit();
        }
    }, [onSubmit]);

    return (
        <input
            {...props}
            className={divClassName}
            type={type}
            value={text || ''}
            name={name}
            onChange={onChange}
            onKeyDown={onKeyDown}
            onBlur={onBlur}
            autoComplete={autoComplete}
            placeholder={placeholder}
        />
    );
};

FormInput.propTypes = {
    text: PropTypes.string,
    type: PropTypes.string,
    name: PropTypes.string.isRequired,
    className: PropTypes.string,
    onChange: PropTypes.func.isRequired,
    onSubmit: PropTypes.func,
    onBlur: PropTypes.func,
    autoComplete: PropTypes.oneOf(['none', 'on']),
    placeholder: PropTypes.string,
};

FormInput.defaultProps = {
    text: null,
    type: 'text',
    className: null,
    onSubmit: null,
    onBlur: null,
    autoComplete: 'none',
    placeholder: null,
};

export default FormInput;