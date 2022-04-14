import { get } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'common/js/Request';

const GET_FEATURE_FLAGS_LIST_URL = '/feature-flags/api/list';

export default class FeatureFlagListStateTransmitter extends StateManager {
    constructor() {
        super();
        this.setState({
            featureFlagListItems: [],
        });
        this.refreshFeatureFlags();
        this.registerMethod(this.openFeatureFlagForm);
        this.registerMethod(this.toggleFeatureFlagStatus);
        this.registerMethod(this.removeFeatureFlag);
    }

    receiveCreateFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagCreated') {
            this.refreshFeatureFlags();
        }
    }

    receiveFeatureFlagModalStateTransmitter(state, metadata) {
        if (metadata === 'refreshFlagList') {
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
        this.sendState('FeatureFlagModalStateTransmitter', { featureFlag }, 'openFeatureFlagModal');
    }

    toggleFeatureFlagStatus(listItemIndex, isOn) {
        const featureFlag = get(this.state.featureFlagListItems, [listItemIndex, 'featureFlag']);
        this.sendState(
            'FeatureFlagFormStateTransmitter',
            { featureFlag, isOn },
            'toggleStatus');
    }

    removeFeatureFlag(listItemIndex) {
        const featureFlag = get(this.state.featureFlagListItems, [listItemIndex, 'featureFlag']);
        this.sendState('FeatureFlagFormStateTransmitter', { id: featureFlag.id }, 'removeFlagById');
    }
}