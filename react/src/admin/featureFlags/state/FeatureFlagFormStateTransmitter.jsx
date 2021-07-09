import { get, isEmpty, set } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import logger from 'common/js/logging';

const FLAG_STATUS_DROPDOWN_OPTIONS = [
    {
        label: 'Enabled',
        value: 'enabled',
    },
    {
        label: 'Disabled',
        value: 'disabled',
    },
];

const USER_SCOPE_DROPDOWN_OPTIONS = [
    {
        label: 'Individual',
        value: 'individual',
    },
    {
        label: 'Public',
        value: 'public',
    },
];

const FIELDS = {
    name: {
        label: 'Flag name',
        name: 'flag-name',
        isRequired: true,
        validate: value => !isEmpty(value),
        value: null,
    },
    status: {
        label: 'Status',
        name: 'flag-status',
        isRequired: true,
        validate: Boolean,
        options: FLAG_STATUS_DROPDOWN_OPTIONS,
    },
    userScope: {
        label: 'User scope',
        name: 'user-scope',
        isRequired: true,
        validate: Boolean,
        options: USER_SCOPE_DROPDOWN_OPTIONS,
    },
    userId: {
        label: 'User ID',
        name: 'user-id',
        isRequired: false,
        validate: Boolean,
    },
};

function isFieldApproved(field) {
    return !field.isRequired || field.isValid;
}

// Abstract
export default class FeatureFlagFormStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            fields: FIELDS,
            canSubmitForm: false,
        });
        this.registerMethod(this.updateFieldValue);
    }

    updateFieldValue(fieldName, value, path = 'value') {
        const { fields } = this.state;
        const { isRequired, validate } = get(fields, [fieldName], {});

        let isValid = true;
        if (isRequired) {
            if (!validate) {
                logger.error(`Create feature flag field "${fieldName}" is required but has no validation function`);
            }
            isValid = validate(value);
        }

        set(fields, [fieldName, 'isValid'], isValid);
        set(fields, [fieldName, path], value);

        const fieldObjects = Object.values(fields);
        const canSubmitForm = fieldObjects.length > 0 && fieldObjects.every(isFieldApproved);

        this.setState({ fields, canSubmitForm });
    }
}