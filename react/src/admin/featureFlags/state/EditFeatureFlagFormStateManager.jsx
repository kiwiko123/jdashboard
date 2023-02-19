import { get, isEmpty, set } from 'lodash';
import FormStateManager from 'tools/forms/state/FormStateManager';
import logger from 'common/js/logging';
import Request from 'tools/http/Request';

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
        name: 'flag-name',
        type: 'input',
        label: 'Flag name',
        value: null,
        isRequired: true,
        validate: value => !isEmpty(value),
    },
    status: {
        name: 'flag-status',
        type: 'dropdownSelector',
        label: 'Status',
        isRequired: true,
        validate: Boolean,
        options: FLAG_STATUS_DROPDOWN_OPTIONS,
    },
    value: {
        name: 'value',
        type: 'input',
        label: 'Value',
        isRequired: false,
        value: null,
    },
    userScope: {
        name: 'user-scope',
        type: 'dropdownSelector',
        label: 'User scope',
        isRequired: true,
        validate: Boolean,
        options: USER_SCOPE_DROPDOWN_OPTIONS,
    },
    userId: {
        name: 'user-id',
        type: 'input',
        label: 'User ID',
        isRequired: false,
        validate: Boolean,
        value: null,
    },
};

export default class EditFeatureFlagFormStateManager extends FormStateManager {
    constructor(featureFlagId, { onSuccessfulEdit } = {}) {
        super();
        this.featureFlagId = featureFlagId;
        this.onSuccessfulEdit = onSuccessfulEdit;

        Request.to(`/feature-flags/api/${this.featureFlagId}`)
            .authenticated()
            .get()
            .then((response) => {
                this.updateFieldValue('name', response.name);
                this.updateFieldValue('status', response.status);
                this.updateFieldValue('value', response.value);
                this.updateFieldValue('userScope', response.userScope);
                this.updateFieldValue('userId', response.userId);
            });
    }

    defaultFields() {
        return FIELDS;
    }

    submitForm() {
        const payload = {
            name: this.state.fields.name.value,
            status: this.state.fields.status.value,
            value: this.state.fields.value.value,
            userScope: this.state.fields.userScope.value,
            userId: this.state.fields.userId.value,
        };
        Request.to(`/feature-flags/api/${this.featureFlagId}`)
            .authenticated()
            .body(payload)
            .patch()
            .then((response) => {
                if (this.onSuccessfulEdit) {
                    this.onSuccessfulEdit();
                }
                this.sendState('FeatureFlagListStateManager', null, 'refresh');
            });
    }
}