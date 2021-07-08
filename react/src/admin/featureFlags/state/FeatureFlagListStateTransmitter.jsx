import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

const GET_FEATURE_FLAGS_LIST_URL = '/feature-flags/api/list';

export default class FeatureFlagListStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            featureFlags: [],
        });

        this.getFeatureFlags().then((response) => {
            const featureFlags = response || [];
            this.setState({ featureFlags });
        });
    }

    getFeatureFlags() {
        return Request.to(GET_FEATURE_FLAGS_LIST_URL)
            .withAuthentication()
            .get();
    }
}