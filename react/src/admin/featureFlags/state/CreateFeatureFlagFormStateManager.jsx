import { get, set } from 'lodash';
import FormStateManager from 'tools/forms/state/FormStateManager';
import logger from 'common/js/logging';
import Request from 'tools/http/Request';
import { FIELDS } from './FeatureFlagFormValues';

export default class CreateFeatureFlagFormStateManager extends FormStateManager {
    constructor({ onSuccessfulCreation } = {}) {
        super();
        this.onSuccessfulCreation = onSuccessfulCreation;
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
        Request.to(`/feature-flags/api`)
            .authenticated()
            .body(payload)
            .post()
            .then((response) => {
                if (this.onSuccessfulCreation) {
                    this.onSuccessfulCreation();
                }
                this.sendState('FeatureFlagListStateManager', null, 'refresh');
            });
    }
}