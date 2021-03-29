import { isUndefined, pickBy } from 'lodash';
import logger from '../../common/js/logging';

export default class CoreStateManger {
    constructor() {
        this.state = {};
        this.__updaters = new Map();
    }

    setState(newState) {
        Object.assign(this.state, newState);
    }

    tryState(newState) {
        const newData = pickBy(newState, (value, key) => !isUndefined(this.state[key]));
        this.setState(newData);
    }

    registerMethod(method) {
        this.setState({ [method.name]: method.bind(this) });
    }

    update() {
        this.__updaters.forEach(updater => updater());
    }

    /**
     * A ComponentStateManager invokes this on its first render.
     * The updater will effectively call setState on the ComponentStateManager to induce a re-render.
     *
     * Do not override this.
     */
    _setUpdater(updater, id) {
        this.__updaters.set(id, updater);
    }

    /**
     * Removes the linked StateTransmitter when it unmounts.
     * Do not override this.
     */
    removeUpdater(id) {
        logger.debug(`Removing ReceivingElement ${id}`);
        this.__updaters.delete(id);
    }

    getState() {
        return this.state;
    }
}