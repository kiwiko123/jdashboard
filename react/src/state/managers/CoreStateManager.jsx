import logger from 'tools/monitoring/logging';

export default class CoreStateManager {
    constructor() {
        this.state = {};
        this.__stateProcessorsById = new Map();
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

    getState() {
        return this.state;
    }
}