import { isNil } from 'lodash';

export function isFieldValid(field) {
    const { value, isRequired, validate, editCount } = field;

    // Don't show blank fields as invalid on the first pass.
    if (!editCount || editCount === 0) {
        return true;
    }

    if (validate) {
        return validate(value);
    }

    if (isRequired) {
        return !isNil(value);
    }

    return true;
}

export function isFormValid(fields) {
    const fieldDefinitions = Object.values(fields);
    return fieldDefinitions.length > 0 && fieldDefinitions.every(field => field.isValid);
}