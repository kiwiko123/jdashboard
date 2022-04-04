import logger from 'common/js/logging';

export default class CoreStateManager {
    constructor() {
        this.state = {};
        this.__stateProcessorsById = new Map();
        this.__updaters = new Map();
    }

    /**
     * One-dimensionally merge the input state into this manager's state.
     */
    setState(newState) {
        Object.assign(this.state, newState);
    }

    registerMethod(method) {
        this.setState({ [method.name]: method.bind(this) });
    }

    setUp({ id, processState }) {
        this.__stateProcessorsById.set(id, processState);
    }

    tearDown({ id }) {
        logger.debug(`Removing state processor ${id}`);
        this.__stateProcessorsById.delete(id);
    }

    /**
     * Induce a re-render in a linked component.
     *
     * Do not override this.
     */
    update() {
        this.__stateProcessorsById.forEach(processState => processState({ state: this.getState() }));
        this.__updaters.forEach(updater => updater()); // Deprecated
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