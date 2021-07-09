import { get, set } from 'lodash';
import CreateFeatureFlagFormStateTransmitter from './CreateFeatureFlagFormStateTransmitter';
import logger from 'common/js/logging';
import Request from 'common/js/Request';

const UPDATE_FEATURE_FLAG_URL = '/feature-flags/api';

export default class EditFeatureFlagFormStateTransmitter extends CreateFeatureFlagFormStateTransmitter {

    constructor(featureFlag) {
        super();
        this.featureFlag = featureFlag;

        const { fields } = this.state;
        set(fields, 'name.value', featureFlag.name);
        set(fields, 'status.value', featureFlag.status);
        set(fields, 'userScope.value', featureFlag.userScope);
        set(fields, 'userId.value', featureFlag.userId);

        this.setState({ fields });
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
            isRemoved: this.featureFlag.isRemoved,
            versions: this.featureFlag.versions,
        };

        Request.to(UPDATE_FEATURE_FLAG_URL)
            .withAuthentication()
            .withBody(payload)
            .put()
            .then((response) => {
                logger.info(`Create feature flag response: ${Object.entries(response)}`);
                this.sendState('FeatureFlagListStateTransmitter', null, 'featureFlagCreated');
            })
    }
}