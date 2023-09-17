import { get, isEmpty, set } from 'lodash';
import FormStateManager from 'tools/forms/state/FormStateManager';
import logger from 'common/js/logging';
import Request from 'tools/http/Request';
import { FIELDS } from './FeatureFlagFormValues';

export default class EditFeatureFlagFormStateManager extends FormStateManager {
    constructor(featureFlagId, { onSuccessfulEdit } = {}) {
        super();
        this.featureFlagId = featureFlagId;
        this.onSuccessfulEdit = onSuccessfulEdit;

        Request.to(`/feature-flags/app-api/${this.featureFlagId}/edit-form-data`)
            .authenticated()
            .get()
            .then((response) => {
                this.updateFieldValue('name', response.featureFlagName);
            });
    }

    defaultFields() {
        return {
            name: {
                name: 'flag-name',
                type: 'input',
                label: 'Flag name',
                value: null,
                isRequired: true,
                validate: value => !isEmpty(value),
            },
        };
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
            .put()
            .then((response) => {
                if (this.onSuccessfulEdit) {
                    this.onSuccessfulEdit();
                }
                this.sendState('FeatureFlagListStateManager', null, 'refresh');
            });
    }
}