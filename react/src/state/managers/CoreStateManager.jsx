import logger from 'tools/monitoring/logging';

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

    tearDown() { }

    /**
     * Induce a re-render in a linked component.
     *
     * Do not override this.
     */
    update() {
        const state = {...this.state}; // Make a copy to ensure that linked components re-render.
        this.__stateProcessorsById.forEach(processState => processState({ state }));

        // Deprecated.
        this.__updaters.forEach(updater => updater());
    }

    // ===            ===
    // === Private    ===
    // ===            ===
    __isActive() {
        return this.__stateProcessorsById.size > 0;
    }

    __linkStateProcessor(id, processState) {
        this.__stateProcessorsById.set(id, processState);
    }

    __unlink(id) {
        this.__stateProcessorsById.delete(id);
    }

    // ===            ===
    // === Deprecated ===
    // ===            ===

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
        logger.debug(`Removing updater ${id}`);
        this.__updaters.delete(id);
    }

    getState() {
        return this.state;
    }
}