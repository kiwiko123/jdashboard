import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

const GET_FEATURE_FLAGS_LIST_URL = '/feature-flags/api/list';

export default class FeatureFlagListStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            featureFlagListItems: [],
        });
        this.refreshFeatureFlags();
    }

    receiveCreateFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagCreated') {
            this.refreshFeatureFlags();
        }
    }

    refreshFeatureFlags() {
        Request.to(GET_FEATURE_FLAGS_LIST_URL)
            .withAuthentication()
            .get()
            .then((response) => {
                this.setState({ featureFlagListItems: response });
            });
    }
}