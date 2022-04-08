import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import logger from 'tools/monitoring/logging';
import InputFormField from 'common/forms/components/core/InputFormField';
import IconButton from 'common/components/IconButton';

function getFieldComponent(type) {
    switch (type) {
        case 'input':
            return InputFormField;
        default:
            logger.error(`Unknown field type "${type}"`);
            return null;
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
    fields, isFormValid, actions, className,
}) => {
    const fieldComponents = getFieldComponents(fields);
    const divClassName = classnames('SimpleForm', className);

    return (
        <div className={divClassName}>
            <div className="fields">
                {fieldComponents}
            </div>
            <div className="form-buttons">
                <IconButton
                    className="submit-form-button"
                    variant="primary"
                    fontAwesomeClassName="fas fa-paper-plane"
                    onClick={actions.submitForm}
                    disabled={!isFormValid}
                >
                    Submit
                </IconButton>
                <IconButton
                    className="reset-form-button"
                    variant="warning"
                    fontAwesomeClassName="fas fa-undo"
                    onClick={actions.clearForm}
                >
                    Reset
                </IconButton>
            </div>
        </div>
    );
};

SimpleForm.propTypes = {
    fields: PropTypes.objectOf(PropTypes.shape({
        name: PropTypes.string.isRequired,
        type: PropTypes.string,
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
};

SimpleForm.defaultProps = {
    fields: {},
    isFormValid: false,
    className: null,
};

export default SimpleForm;