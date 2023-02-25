import StateManager from 'state/StateManager';

export default class FeatureFlagModalDelegateStateManager extends StateManager {
    constructor({ openModal, closeModal }) {
        super();
        this.openModal = openModal;
        this.closeModal = closeModal;
        this.setState({
            modal: null,
        });
        this.registerMethod(this.close);
    }

    receiveFeatureFlagPageToolbarStateManager(state, metadata) {
        if (metadata === 'createNewFeatureFlag') {
            this.setState({
                modal: {
                    id: 'createFeatureFlagForm',
                },
            });
            this.openModal();
        }
    }

    receiveFeatureFlagListStateManager(state, metadata) {
        if (metadata === 'editFeatureFlagForm') {
            this.setState({
                modal: {
                    id: 'editFeatureFlagForm',
                    data: {
                        featureFlagId: state.featureFlagId,
                    },
                },
            });
            this.openModal();
        }
    }

    close() {
        this.closeModal();
        this.setState({ modal: null });
    }
}