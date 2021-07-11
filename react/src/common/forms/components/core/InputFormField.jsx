import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import FormField from './FormField';
import FormInput from '../FormInput';
import fieldPropTypeShape from './propTypes/fieldPropTypeShape';

const InputFormField = ({
    className, label, name, onChange, isRequired, isValid, validate, value, ...props,
}) => {
    const divClassName = classnames('InputFormField', className);
    return (
        <FormField
            label={label}
            isRequired={isRequired}
            isValid={isValid}
            className={divClassName}
        >
            <FormInput
                {...props}
                text={value}
                name={name}
                onChange={onChange}
            />
        </FormField>
    );
};

InputFormField.propTypes = {
    ...PropTypes.shape(fieldPropTypeShape),
    className: PropTypes.string,
};

InputFormField.defaultProps = {
    className: null,
    isRequired: false,
    isValid: true,
    value: null,
};

export default InputFormField;