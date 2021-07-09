import { get, isEmpty, set } from 'lodash';
import FeatureFlagFormStateTransmitter from './FeatureFlagFormStateTransmitter';
import logger from 'common/js/logging';
import Request from 'common/js/Request';

const CREATE_FEATURE_FLAG_URL = '/feature-flags/api';

export default class CreateFeatureFlagFormStateTransmitter extends FeatureFlagFormStateTransmitter {
    constructor() {
        super();
        this.registerMethod(this.submitForm);
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
        };

        Request.to(CREATE_FEATURE_FLAG_URL)
            .withAuthentication()
            .withBody(payload)
            .post()
            .then((response) => {
                logger.info(`Create feature flag response: ${Object.entries(response)}`);
                this.sendState('FeatureFlagListStateTransmitter', null, 'featureFlagCreated');
                this.sendState('FeatureFlagModalStateTransmitter', null, 'featureFlagCreated');
            })
    }
}