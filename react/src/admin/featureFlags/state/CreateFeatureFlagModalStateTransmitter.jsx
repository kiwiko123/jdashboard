import { get } from 'lodash';
import StateTransmitter from '../../../state/StateTransmitter';

export default class CreateFeatureFlagModalStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({
            isOpen: false,
        });
        this.registerMethod(this.close);
    }

    receiveState(state, { tag, event }) {
        if (tag === 'FeatureFlagToolbarStateTransmitter') {
            if (event === 'pressCreateButton') {
                this.setState({ isOpen: state.isOpen });
            }
        }
    }

    close() {
        this.setState({ isOpen: false });
    }
}