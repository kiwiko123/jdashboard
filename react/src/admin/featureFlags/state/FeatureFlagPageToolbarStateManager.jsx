import { debounce } from 'lodash';
import StateManager from 'state/StateManager';

export default class FeatureFlagPageToolbarStateManager extends StateManager {
    constructor() {
        super();

        this.searchFeatureFlag = debounce(this.searchFeatureFlag.bind(this), 1000);

        this.setState({
            searchInput: null,
            onSearchInputChange: this.onSearchInputChange.bind(this),
            openCreateFeatureFlagModal: this.openCreateFeatureFlagModal.bind(this),
        });
    }

    onSearchInputChange(event) {
        const searchInput = event.target.value;
        this.setState({ searchInput });
        this.searchFeatureFlag(searchInput);
    }

    searchFeatureFlag(searchInput) {

    }

    openCreateFeatureFlagModal() {
        this.sendState('FeatureFlagModalDelegateStateManager', null, 'createNewFeatureFlag');
    }
}