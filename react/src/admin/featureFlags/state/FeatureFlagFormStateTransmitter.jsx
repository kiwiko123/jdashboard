import { get, isEmpty, set } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import logger from 'common/js/logging';
import Request from 'common/js/Request';

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
    value: {
        label: 'Value',
        name: 'value',
        isRequired: false,
        value: null,
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
        value: null,
    },
};

function isFieldApproved(field) {
    return !field.isRequired || field.isValid;
}

function createFields(fields) {
    const result = {};
    Object.entries(fields)
        .forEach(([name, field]) => {
            result[name] = field;
        });
    return result;
}

export default class FeatureFlagFormStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.featureFlag = null;
        this.setState({
            fields: createFields(FIELDS),
            canSubmitForm: false,
        });
        this.registerMethod(this.updateFieldValue);
        this.registerMethod(this.submitForm);
    }

    receiveFeatureFlagModalStateTransmitter(state, metadata) {
        if (metadata === 'setFeatureFlag') {
            this.setFeatureFlag(state.featureFlag);
        } else if (metadata === 'reset') {
            this.setFeatureFlag(null);
        }
    }

    receiveFeatureFlagListStateTransmitter(state, metadata) {
        if (metadata === 'toggleStatus') {
            this.setFeatureFlag(state.featureFlag);
            const status = state.isOn ? 'enabled' : 'disabled';
            this.updateFieldValue('status', status);
            this.submitForm();
        } else if (metadata === 'removeFlagById') {
            this._deleteFeatureFlag(state.id);
        }
    }

    setFeatureFlag(featureFlag) {
        this.featureFlag = featureFlag;

        let fields = createFields(FIELDS);
        set(fields, 'name.value', get(featureFlag, 'name'));
        set(fields, 'status.value', get(featureFlag, 'status'));
        set(fields, 'value.value', get(featureFlag, 'value'));
        set(fields, 'userScope.value', get(featureFlag, 'userScope'));
        set(fields, 'userId.value', get(featureFlag, 'userId'));

        fields = createFields(fields); // run validation
        const fieldObjects = Object.values(fields);
        const canSubmitForm = fieldObjects.length > 0 && fieldObjects.every(isFieldApproved);

        this.setState({ fields, canSubmitForm });
    }

    updateFieldValue(fieldName, value, path = 'value') {
        const { fields } = this.state;
        const { isRequired, validate } = get(fields, [fieldName], {});

        let isValid = true;
        if (isRequired) {
            if (!validate) {
                logger.error(`Feature flag form field "${fieldName}" is required but has no validation function`);
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
        this.setState({ canSubmitForm: false });
        const payload = {
            // Required fields
            name: this.state.fields.name.value,
            status: this.state.fields.status.value,

            // Optional fields
            value: this.state.fields.value.value,
            userScope: this.state.fields.userScope.value,
            userId: this.state.fields.userId.value,

            // Non-editable fields
            isRemoved: get(this.featureFlag, 'isRemoved'),
            versions: get(this.featureFlag, 'versions'),
        };

        const isCreatingNewFeatureFlag = !this.featureFlag;
        if (isCreatingNewFeatureFlag) {
            this._createFeatureFlag(payload);
        } else {
            this._editFeatureFlag(payload);
        }
    }

    _createFeatureFlag(payload) {
        Request.to('/feature-flags/api')
            .withAuthentication()
            .withBody(payload)
            .post()
            .then((response) => {
                logger.info(`Create feature flag response: ${Object.entries(response)}`);
                this.sendState('FeatureFlagModalStateTransmitter', null, 'featureFlagSaved');
            })
    }

    _editFeatureFlag(payload) {
        Request.to(`/feature-flags/api/${this.featureFlag.id}`)
            .withAuthentication()
            .withBody(payload)
            .patch()
            .then((response) => {
                logger.info(`Create feature flag response: ${Object.entries(response)}`);
                this.sendState('FeatureFlagModalStateTransmitter', null, 'featureFlagSaved');
                this.featureFlag = null;
            })
    }

    _deleteFeatureFlag(featureFlagId) {
        Request.to(`feature-flags/api/${featureFlagId}`)
            .withAuthentication()
            .delete()
            .then((response) => {
                this.sendState('FeatureFlagModalStateTransmitter', null, 'featureFlagSaved');
            });
    }
}