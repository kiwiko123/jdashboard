import { isUndefined, pickBy } from 'lodash';
import logger from 'common/js/logging';

export default class CoreStateManger {
    constructor() {
        this.state = {};
        this.__updaters = new Map();
    }

    /**
     * One-dimensionally merge the input state into this manager's state.
     */
    setState(newState) {
        Object.assign(this.state, newState);
    }

    /**
     * One-dimensionally merge keys in the input state that are not currently present in this manager's state.
     * Existing keys are ignored.
     */
    tryState(newState) {
        const newData = pickBy(newState, (value, key) => !isUndefined(this.state[key]));
        this.setState(newData);
    }

    registerMethod(method) {
        this.setState({ [method.name]: method.bind(this) });
    }

    /**
     * Induce a re-render a linked component.
     *
     * Do not override this.
     */
    update() {
        this.__updaters.forEach(updater => updater());
    }

    /**
     * A ComponentStateManager invokes this on its first render.
     * The updater should induce a re-render in the linked component.
     *
     * Do not override this.
     */
    _setUpdater(updater, id) {
        this.__updaters.set(id, updater);
    }

    /**
     * Removes the given updater. This should be invoked when a linked component unmounts.
     *
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