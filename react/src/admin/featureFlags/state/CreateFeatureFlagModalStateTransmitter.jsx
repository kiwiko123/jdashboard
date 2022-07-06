import { get } from 'lodash';
import StateManager from 'state/StateManager';

export default class CreateFeatureFlagModalStateTransmitter extends StateManager {
    constructor() {
        super();
        this.setState({
            isOpen: false,
        });
        this.registerMethod(this.close);
    }

    receiveFeatureFlagToolbarStateTransmitter(state, metadata) {
        if (metadata === 'pressCreateButton') {
            this.setState({ isOpen: state.isOpen });
        }
    }

    receiveCreateFeatureFlagFormStateTransmitter(state, metadata) {
        if (metadata === 'featureFlagCreated') {
            this.close();
        }
    }

    close() {
        this.setState({ isOpen: false });
    }
}