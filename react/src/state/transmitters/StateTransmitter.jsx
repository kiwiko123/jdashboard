import { isEmpty, isUndefined, pickBy } from 'lodash';
import logger from 'common/js/logging';
import CoreStateManager from '../managers/CoreStateManager';
import StateTransmitterRegistry from './private/StateTransmitterRegistry';

let INSTANCE_ID = 0;

export default class StateTransmitter extends CoreStateManager {
    constructor() {
        super();
        this.tag = this.constructor.name;
        this.id = `${this.tag}-${INSTANCE_ID++}`;

        StateTransmitterRegistry.register(this);
    }

    setState(newState) {
        super.setState(newState);
        this.update();
    }

    sendState(tag, state, metadata) {
        const data = isUndefined(state) ? this.state : state;
        StateTransmitterRegistry.sendState(this.tag, data, tag, metadata);
    }

    receiveState(tag, state, metadata) {
        logger.warn(`No implementation for receive in ${this.id}`);
    }

    destroy() {
        StateTransmitterRegistry.deregister(this);
    }
}