import React from 'react';
import PropTypes from 'prop-types';
import FormField from './FormField';
import DropdownSelector from '../DropdownSelector';
import fieldPropTypeShape from './propTypes/fieldPropTypeShape';

const DropdownSelectorFormField = ({
    className, label, name, options, onChange, isRequired, isValid, validate, value, ...props,
}) => {
    return (
        <FormField
            label={label}
            isRequired={isRequired}
        >
            <DropdownSelector
                {...props}
                options={options}
                onSelect={onChange}
                preSelectedValue={value}
            />
        </FormField>
    );
};

DropdownSelectorFormField.propTypes = {
    ...PropTypes.shape(fieldPropTypeShape),
    className: PropTypes.string,
    options: PropTypes.arrayOf(PropTypes.shape({
        label: PropTypes.string.isRequired,
        value: PropTypes.any.isRequired,
    })).isRequired,
};

DropdownSelectorFormField.defaultProps = {
    className: null,
    isRequired: false,
    isValid: true,
    value: null,
};

export default DropdownSelectorFormField;