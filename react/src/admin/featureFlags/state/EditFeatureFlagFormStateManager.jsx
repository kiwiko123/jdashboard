import { get, set } from 'lodash';
import FormStateManager from 'tools/forms/state/FormStateManager';
import logger from 'common/js/logging';
import Request from 'tools/http/Request';
import { FIELDS } from './FeatureFlagFormValues';

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
            .put()
            .then((response) => {
                if (this.onSuccessfulEdit) {
                    this.onSuccessfulEdit();
                }
                this.sendState('FeatureFlagListStateManager', null, 'refresh');
            });
    }
}