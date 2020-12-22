import { registerAction, removeAction } from './actions';
import logger from '../common/js/logging';
import { isPresentInListeners } from './helpers/util';

let instanceId = 0;

/**
 * State management class that broadcasts data to either a component, or other broadcasters.
 * A broadcaster is a singleton that's meant to abstract business logic away from components.
 * A broadcaster can be tied to a ComponentStateManager, which accepts a React component as a prop;
 * invoking the broadcaster's setState method will "broadcast" its state to the component and trigger a re-render.
 * A broadcaster can also "listen" to other broadcasters, and receive their state when they call setState.
 *
 * Extend this class and invoke setState to propagate data through to listeners.
 */
export default class DefaultBroadcaster {

    static getId() {
        return this.name;
    }

    /**
     * Constructs a new Broadcaster instance.
     * If a derived broadcaster defines a constructor, be sure to invoke super() first.
     */
    constructor() {
        this.state = {};
        this.__listeners = new Set();
        this.__instanceId = instanceId++;
        this.__actionNames = new Set();
        this.__enabled = true;

        this.register = this.register.bind(this);
        this.__updaters = new Map();
        this.__stateQueue = [];
    }

    /**
     * One-dimensionally merges the input state with this broadcaster's state.
     * Passes state into all registered ComponentStateManagers and listening broadcasters.
     *
     * Note: setState invocations are usually, but not necessarily, one-to-one with instantaneous state updates / re-renders.
     * Optimizations may be in place to reduce the amount of re-renders.
     *
     * Do not override this.
     */
    setState(newState) {
        this.__stateQueue.push(newState);
        if (this.canUpdate()) {
            this.update();
        }
    }

    /**
     * When a broadcaster calls setState, listeners will receive its state through this method.
     * The invoking broadcaster can be identified through the broadcasterId argument.
     * Override this to control state ingestion.
     */
    receive(state, broadcasterId) {
        logger.warn(`No implementation for receive in ${this.constructor.getId()}`);
    }

    /**
     * Subscribes this to the argument broadcaster;
     * that is, when the argument broadcaster makes state updates, this will receive it.
     *
     * If a cycle is detected within the listeners, this operation will silently fail.
     *
     * When listenTo is called, this will immediately receive the broadcaster's state.
     */
    listenTo(broadcaster) {
        broadcaster.register(this);
    }

    /**
     * This method is the converse of listenTo.
     * Do not override this.
     */
    register(broadcaster) {
        if (this.__listeners.has(broadcaster)) {
            logger.info(`Attempting to register duplicate broadcaster ${broadcaster.constructor.getId()}`);
            return;
        }
        if (isPresentInListeners(broadcaster, this.constructor.getId())) {
            logger.error(`Failed to register ${broadcaster.constructor.getId()}; cycle detected in listeners`);
            return;
        }
        this.__listeners.add(broadcaster);
        broadcaster.receive(this.getState(), this.constructor.getId());
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
     * Return this broadcaster's state.
     * Returning a copy of the state may be safer, but returning a reference may be more performant.
     */
    getState() {
        return this.state;
    }

    /**
     * Determines if the ReceivingElement is eligible to re-render.
     * The act of this method returning true makes no guarantees that the ReceivingElement _will_ re-render;
     * rather, it's a signal that it _can_.
     */
    canUpdate() {
        return this.__enabled;
    }

    update() {
        this._updateState();
        const state = this.getState();
        const id = this.constructor.getId();

        this.__listeners.forEach(broadcaster => broadcaster.receive(state, id));
        this.__updaters.forEach(updater => updater());
        logger.debug(`${id}-${this.__instanceId} just updated`);
    }

    /**
     * Removes the linked ReceivingElement when it unmounts.
     * Do not override this.
     */
    removeUpdater(id) {
        logger.debug(`Removing ReceivingElement ${id}`);
        this.__updaters.delete(id);
    }

    destroy() {
        this.disable();
        this.__actionNames.forEach(name => removeAction(this.constructor.getId(), name, this.id));
        logger.debug(`Destroyed broadcaster ${this.constructor.getId()}-${this.__instanceId}`);
    }

    enable() {
        this.__enabled = true;
        this.update();
    }

    disable() {
        this.__enabled = false;
    }

    registerMethod(method) {
        this.setState({
            [method.name]: method.bind(this),
        });
    }

    _updateState() {
        if (this.__stateQueue.length === 0) {
            return;
        }

        logger.debug(`[${this.constructor.getId()}] State queue length: ${this.__stateQueue.length}`);

        // Pending updates will likely be smaller than `this.state`;
        // by first building up an object with all pending state changes,
        // the overall number of fields being `Object.assign`ed should be reduced.
        const newState = {};
        this.__stateQueue.forEach(pendingState => Object.assign(newState, pendingState));
        this.__stateQueue = [];
        Object.assign(this.state, newState);
    }

    // ==============
    // Global actions
    // ==============

    registerGlobalAction(name, callback) {
        callback.broadcasterInstanceId = this.__instanceId;
        registerAction(this.constructor.getId(), name, callback);
        this.__actionNames.add(name);
    }
}