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
        this.registerMethod(this.toggleFeatureFlagStatusForMe);
        this.registerMethod(this.toggleFeatureFlagStatusForPublic);
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
        Request.to('/feature-flags/app-api/list')
            .authenticated()
            .get()
            .then((response) => {
                const listItems = response.listItems.map(item => ({
                    id: item.featureFlagId,
                    name: item.featureFlagName,
                    createdDate: new Date(item.createdDate).toLocaleString(),
                    lastUpdatedDate: new Date(item.lastUpdatedDate).toLocaleString(),
                    isOnForMe: item.isOnForMe,
                    isOnForPublic: item.isOnForPublic,
                    rules: item.rules,
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

    toggleFeatureFlagStatus(listItemIndex, scope) {
        const { featureFlagListItems } = this.state;
        featureFlagListItems[listItemIndex].disabled = true;
        featureFlagListItems[listItemIndex].isLoading = true;
        this.setState({ featureFlagListItems });

        const payload = {
            featureFlagName: get(featureFlagListItems, [listItemIndex, 'name']),
            userScope: scope,
        };

        Request.to('/feature-flags/app-api/flags/toggle')
            .authenticated()
            .body(payload)
            .post()
            .then(() => {
                this.refreshFeatureFlags();
            });
    }

    toggleFeatureFlagStatusForMe(listItemIndex) {
        this.toggleFeatureFlagStatus(listItemIndex, 'individual');
    }

    toggleFeatureFlagStatusForPublic(listItemIndex) {
        this.toggleFeatureFlagStatus(listItemIndex, 'public');
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