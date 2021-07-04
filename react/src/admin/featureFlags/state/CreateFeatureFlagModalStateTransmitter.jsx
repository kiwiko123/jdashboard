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

    close() {
        this.setState({ isOpen: false });
    }
}