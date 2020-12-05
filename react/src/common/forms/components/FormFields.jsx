import React, { useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { isNil } from 'lodash';
import FormField from './FormField';
import Banner, { BANNER_TYPES } from '../../components/Banner';
import IconButton from '../../components/IconButton';

import './FormFields.css';

const FormFields = ({
    fields, submitButtonProps, buttons, className,
}) => {
    let fieldErrorBanner;
    const invalidFields = fields.filter(field => field.isValid === false) // default/undefined case is considered valid
        .map(field => ({
            name: field.name,
            label: field.label,
        }));
    if (invalidFields.length > 0) {
        const fieldErrors = invalidFields.map(field => (
            <li
                key={field.name}
                className={`field-error-${field.name}`}
            >
                {field.label}
            </li>
        ));
        let message = invalidFields.length === 1
            ? 'There\'s an error with the following field.'
            : 'There are errors with the following fields.';
        fieldErrorBanner = (
            <Banner
                className="field-error-banner"
                type={BANNER_TYPES.danger}
            >
                <div className="field-error-container">
                    <div className="field-error-message">
                        {message}
                    </div>
                    <ol className="field-errors">
                        {fieldErrors}
                    </ol>
                </div>
            </Banner>
        );
    }
    const [isSubmitEnabled, setIsSubmitEnabled] = useState(invalidFields.length === 0);
    const onSubmit = () => {
        submitButtonProps.onClick();
        setIsSubmitEnabled(false);
    };
    const fieldElements = fields.map(field => (
            <FormField
                {...field}
                onSubmit={onSubmit}
                key={field.name}
            />
        ));
    let buttonElements;
    if (buttons.length > 0) {
        buttonElements = buttons.map(button => (
            <IconButton
                {...button}
                key={button.id}
            />
        ));
    }
    const divClassName = classnames('FormFields', className);

    return (
        <div className={divClassName}>
            {fieldErrorBanner}
            {fieldElements}
            <div className="buttons">
                <IconButton
                    className="button-submit"
                    {...submitButtonProps}
                    disabled={!isSubmitEnabled}
                    onClick={onSubmit}
                />
                {buttonElements}
            </div>
        </div>
    );
};

FormFields.propTypes = {
    fields: PropTypes.arrayOf(FormField.propTypes).isRequired,
    submitButtonProps: PropTypes.shape(IconButton.propTypes).isRequired,
    buttons: PropTypes.arrayOf(PropTypes.shape({
        ...IconButton.propTypes,
        id: PropTypes.string.isRequired,
    })),
    className: PropTypes.string,
};

FormFields.defaultProps = {
    buttons: [],
    className: null,
};

export default FormFields;