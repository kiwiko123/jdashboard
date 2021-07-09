import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';

export default class FeatureFlagModalStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            isOpen: false,
            featureFlag: null,
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

    receiveCreateFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagCreated') {
            this.close();
        }
    }

    receiveEditFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagEdited') {
            this.close();
        }
    }

    receiveFeatureFlagListStateTransmitter(state, metadata) {
        if (metadata === 'openEditFeatureFlagModal') {
            this.sendState('EditFeatureFlagFormStateTransmitter', { featureFlag: state.featureFlag }, 'setFeatureFlag');
            this.setState({
                isOpen: true,
                formType: 'edit',
                title: 'Edit Feature Flag',
            });
        }
    }

    close() {
        this.setState({
            featureFlag: null,
            isOpen: false,
            formType: null,
            title: null,
        });
    }
}