import StateTransmitter from 'state/StateTransmitter';

export default class FeatureFlagToolbarStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.registerMethod(this.pressCreateButton);
    }

    pressCreateButton() {
        this.sendState('FeatureFlagModalStateTransmitter', null, 'pressCreateButton');
    }
}