import { get, isEmpty, set } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import logger from 'common/js/logging';
import Request from 'common/js/Request';

const CREATE_FEATURE_FLAG_URL = '/feature-flags/api';

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

export default class CreateFeatureFlagFormStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            fields: FIELDS,
            canSubmitForm: false,
        });
        this.registerMethod(this.updateFieldValue);
        this.registerMethod(this.submitForm);
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

    submitForm() {
        const payload = {
            name: this.state.fields.name.value,
            status: this.state.fields.status.value,

            // Optional fields
            userScope: this.state.fields.userScope.value,
            userId: this.state.fields.userId.value,
            // TODO value
        };

        Request.to(CREATE_FEATURE_FLAG_URL)
            .withAuthentication()
            .withBody(payload)
            .post()
            .then((response) => {
                // TODO
                logger.info(`Create feature flag response: ${Object.entries(response)}`);
            });
    }
}