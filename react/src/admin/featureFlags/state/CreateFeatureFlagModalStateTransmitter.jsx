import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';

export default class CreateFeatureFlagModalStateTransmitter extends StateTransmitter {
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