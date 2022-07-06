import StateManager from 'state/StateManager';

export default class FeatureFlagModalStateTransmitter extends StateManager {
    constructor() {
        super();
        this.setState({
            isOpen: false,
            formType: null,
            title: null,
        });
        this.registerMethod(this.close);
    }

    receiveFeatureFlagToolbarStateTransmitter(state, metadata) {
        if (metadata === 'pressCreateButton') {
            this.setState({
                isOpen: true,
                formType: 'create',
                title: 'Create Feature Flag',
            });
        }
    }

    receiveFeatureFlagListStateTransmitter(state, metadata) {
        if (metadata === 'openFeatureFlagModal') {
            this.setState({
                isOpen: true,
                formType: 'edit',
                title: 'Edit Feature Flag',
            });
            this.sendState('FeatureFlagFormStateTransmitter', { featureFlag: state.featureFlag }, 'setFeatureFlag');
        }
    }

    receiveFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagSaved') {
            this.sendState('FeatureFlagListStateTransmitter', null, 'refreshFlagList');
            this.close();
        }
    }

    close() {
        this.setState({
            isOpen: false,
            formType: null,
            title: null,
        });
        this.sendState('FeatureFlagFormStateTransmitter', null, 'reset');
    }
}