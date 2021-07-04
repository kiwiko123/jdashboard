import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import FormField from './FormField';
import Banner, { BANNER_TYPES } from '../../components/Banner';
import IconButton from '../../components/IconButton';
import { useEventSubmit } from '../../../state/hooks';

import './FormFields.css';

function makeFieldErrorBanner(invalidFields) {
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
    return (
        <Banner
            className="field-error-banner"
            type={BANNER_TYPES.danger}
            iconSizeFactor={2}
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

const FormFields = ({
    fields, submitButtonProps, extraButtons, className,
}) => {
    const [hasBlurred, setHasBlurred] = useState(false);
    const invalidFields = fields.filter(field => field.isValid === false) // default/undefined case is considered valid
        .map(field => ({
            name: field.name,
            label: field.label,
        }));
    const fieldErrorBanner = invalidFields.length > 0 && hasBlurred && makeFieldErrorBanner(invalidFields);
    const [canSubmit, setCanSubmit] = useState(invalidFields.length === 0);
    useEffect(() => {
        setCanSubmit(invalidFields.length === 0);
    }, [hasBlurred, invalidFields.length]);
    const onSubmit = useEventSubmit(() => {
        if (!canSubmit) {
            return;
        }
        submitButtonProps.onClick();
        setCanSubmit(false);
    }, [canSubmit]);
    const fieldElements = fields.map(field => (
        <FormField
            {...field}
            key={field.name}
            onBlur={() => setHasBlurred(true)}
            isValid={!hasBlurred || field.isValid}
        />
    ));
    const extraButtonElements = extraButtons.length > 0 && extraButtons.map(button => (
        <IconButton
            {...button}
            key={button.id}
        />
    ));;
    const divClassName = classnames('FormFields', className);

    return (
        <div className={divClassName}>
            {fieldErrorBanner}
            <div
                className="form"
                onKeyDown={onSubmit}
            >
                {fieldElements}
                <div className="buttons">
                    <IconButton
                        className="button-submit"
                        {...submitButtonProps}
                        disabled={!canSubmit}
                        onClick={onSubmit}
                    />
                    {extraButtonElements}
                </div>
            </div>
        </div>
    );
};

FormFields.propTypes = {
    fields: PropTypes.arrayOf(PropTypes.shape(FormField.propTypes)).isRequired,
    submitButtonProps: PropTypes.shape(IconButton.propTypes).isRequired,
    extraButtons: PropTypes.arrayOf(PropTypes.shape({
        ...IconButton.propTypes,
        id: PropTypes.string.isRequired,
    })),
    className: PropTypes.string,
};

FormFields.defaultProps = {
    extraButtons: [],
    className: null,
};

export default FormFields;