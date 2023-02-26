import { get } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';

export default class FeatureFlagListStateManager extends StateManager {
    constructor() {
        super();
        this.setState({
            featureFlagListItems: [],
        });

        this.registerMethod(this.editFeatureFlagForm);
        this.registerMethod(this.toggleFeatureFlagStatus);
        this.registerMethod(this.removeFeatureFlag);

        this.refreshFeatureFlags();
    }

    receiveEditFeatureFlagFormStateManager(state, metadata) {
        if (metadata === 'refresh') {
            this.refreshFeatureFlags();
        }
    }

    receiveCreateFeatureFlagFormStateManager(state, metadata) {
        if (metadata === 'refresh') {
            this.refreshFeatureFlags();
        }
    }

    refreshFeatureFlags() {
        Request.to('/feature-flags/api/list')
            .authenticated()
            .get()
            .then((response) => {
                const listItems = response.payload.map(item => ({
                    ...item,
                    isLoading: false,
                    disabled: false,
                }));
                this.setState({ featureFlagListItems: listItems });
            });
    }

    editFeatureFlagForm(featureFlagIndex) {
        const featureFlag = get(this.state.featureFlagListItems, [featureFlagIndex, 'featureFlag']);
        const state = {
            featureFlagId: featureFlag.id,
        };
        this.sendState('FeatureFlagModalDelegateStateManager', state, 'editFeatureFlagForm');
    }

    toggleFeatureFlagStatus(listItemIndex, isOn) {
        const { featureFlagListItems } = this.state;
        featureFlagListItems[listItemIndex].disabled = true;
        featureFlagListItems[listItemIndex].isLoading = true;
        this.setState({ featureFlagListItems });

        const featureFlagId = get(featureFlagListItems, [listItemIndex, 'featureFlag', 'id']);
        Request.to(`/feature-flags/app-api/${featureFlagId}/status/toggle`)
            .authenticated()
            .post()
            .then(() => {
                this.refreshFeatureFlags();
            });
    }

    removeFeatureFlag(listItemIndex) {
        const featureFlagId = get(this.state.featureFlagListItems, [listItemIndex, 'featureFlag', 'id']);
        Request.to(`/feature-flags/api/${featureFlagId}`)
            .authenticated()
            .delete()
            .then(() => {
                this.refreshFeatureFlags();
            });
    }
}