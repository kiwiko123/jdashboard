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
        this.registerMethod(this.openFeatureFlagForm);
        this.registerMethod(this.toggleFeatureFlagStatus);
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

    openFeatureFlagForm(featureFlagIndex) {
        const featureFlag = get(this.state.featureFlagListItems, [featureFlagIndex, 'featureFlag']);
        this.sendState('FeatureFlagModalStateTransmitter', { featureFlag }, 'openEditFeatureFlagModal');
    }

    toggleFeatureFlagStatus(listItemIndex, isOn) {
        const featureFlag = get(this.state.featureFlagListItems, [listItemIndex, 'featureFlag']);
        this.sendState(
            'EditFeatureFlagFormStateTransmitter',
            { featureFlag, isOn },
            'toggleStatus');
    }
}