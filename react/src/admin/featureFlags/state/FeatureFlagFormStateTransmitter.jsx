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

export default class FeatureFlagFormStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.featureFlag = null;
        this.setState({
            fields: FIELDS,
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
        if (!featureFlag) {
            return;
        }
        const { fields } = this.state;
        set(fields, 'name.value', featureFlag.name);
        set(fields, 'status.value', featureFlag.status);
        set(fields, 'userScope.value', featureFlag.userScope);
        set(fields, 'userId.value', featureFlag.userId);

        this.setState({ fields });
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
        this.setState({ canSubmitForm: false });
        const payload = {
            name: this.state.fields.name.value,
            status: this.state.fields.status.value,

            // Optional fields
            userScope: this.state.fields.userScope.value,
            userId: this.state.fields.userId.value,
            // TODO value

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
            .put()
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