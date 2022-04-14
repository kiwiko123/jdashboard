import StateManager from 'state/StateManager';

export default class FeatureFlagToolbarStateTransmitter extends StateManager {
    constructor() {
        super();
        this.registerMethod(this.pressCreateButton);
    }

    pressCreateButton() {
        this.sendState('FeatureFlagModalStateTransmitter', null, 'pressCreateButton');
    }
}