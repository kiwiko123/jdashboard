export default class CoreStateManager {
    constructor() {
        this.state = {};
        this.__stateProcessorsById = new Map();
        this.__setStateCount = 0;
    }

    /**
     * One-dimensionally merge the input state into this manager's state.
     */
    setState(newState) {
        Object.assign(this.state, newState);
        ++this.__setStateCount;
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
        this.__stateProcessorsById.forEach((processState) => {
            processState({ state });
        });
    }

    // ===            ===
    // === Private    ===
    // ===            ===

    __linkStateProcessor(id, processState) {
        this.__stateProcessorsById.set(id, processState);
    }

    __unlink(id) {
        this.__stateProcessorsById.delete(id);
    }
}