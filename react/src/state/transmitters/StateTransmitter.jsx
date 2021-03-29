import { isEmpty, isUndefined, pickBy } from 'lodash';
import logger from '../../common/js/logging';
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
        Object.assign(this.state, newState);
        this.update();
    }

    sendState(tag, { state, event } = {}) {
        const data = isUndefined(state) ? this.state : state;
        StateTransmitterRegistry.sendState(this.tag, data, tag, { event });
    }

    receiveState(state, { tag, event } = {}) {
        logger.warn(`No implementation for receive in ${this.id}`);
    }

    destroy() {
        StateTransmitterRegistry.deregister(this);
    }
}