import { throttle } from 'lodash';
import { registerAction, removeAction } from './actions';
import logger from '../common/js/logging';

function some(iterable, predicate) {
    for (const item of iterable) {
        if (predicate(item)) {
            return true;
        }
    }
    return false;
}

/**
 * Recursively checks if the current broadcaster is present in any of its listeners.
 * Returns true if any sub-listener links to the broadcaster defined by `id`.
 */
function isPresentInListeners(broadcaster, id) {
    return broadcaster.constructor.getId() === id || some(broadcaster.__listeners, listener => isPresentInListeners(listener, id));
}

let instanceId = 0;

/**
 * State management class that broadcasts data to either a component, or other broadcasters.
 * A broadcaster is meant to abstract business logic away from components.
 * A broadcaster can have other "listener" broadcasters that are registered to it.
 * A broadcaster can also be tied to a ReceivingElement, which accepts a React component as its child;
 * effectively passing its state to a component via props.
 *
 * Calls to setState will pass the broadcaster's entire state into all of its listeners,
 * including registered broadcasters and the ReceivingElement to which it's linked.
 *
 * Extend this class and invoke setState to propagate data through to listeners.
 */
export default class Broadcaster {

    static getId() {
        return this.name;
    }

    /**
     * Constructs a new Broadcaster instance.
     * If a derived broadcaster defines a constructor, always invoke super().
     * The `broadcasters` argument is an array of broadcasters that will each be immediately registered and listening to this.
     */
    constructor() {
        // "Override" this to control the minimum duration, in milliseconds, between re-renders.
        this.reRenderMillis = 50;
        this.state = {};
        this.__listeners = new Set();
        this.__instanceId = instanceId++;
        this.__actionNames = new Set();
        this.__enabled = true;

        this.register = this.register.bind(this);
        this.update = throttle(this.update, this.reRenderMillis, { leading: false, trailing: true });
        this.__updaters = new Map();
    }

    /**
     * One-dimensionally merges the input state with this broadcaster's state.
     * Passes state into listening ReceivingElements and broadcasters.
     * However, Calling setState does not guarantee an instantaneous update to listeners.
     * Do not override this.
     */
    setState(newState) {
        Object.assign(this.state, newState);
        if (this.canUpdate()) {
            this.update();
        }
    }

    /**
     * When a broadcaster calls setState, registered listeners will receive that state through this method.
     * The invoking broadcaster can be identified through the broadcasterId argument.
     * Override this to control state ingestion.
     */
    receive(state, broadcasterId) {
        logger.warn(`No implementation for receive in ${this.constructor.getId()}`);
    }

    /**
     * Registers the given broadcaster with this broadcaster.
     * Immediately funnels this.state into the newly-registered broadcaster.
     * There cannot be any cycles within the listener graph;
     * if one is detected, the broadcaster will not be registered.
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
     * The converse of register.
     * Updates to the argument broadcaster will be received by this.
     */
    listenTo(broadcaster) {
        broadcaster.register(this);
    }

    /**
     * A ReceivingElement invokes this on its first render.
     * The updater will effectively call setState on the ReceivingElement to induce a re-render.
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
        const state = this.getState();
        const id = this.constructor.getId();

        this.__listeners.forEach(broadcaster => broadcaster.receive(state, id));
        this.__updaters.forEach(updater => updater());
        logger.debug(`${this.constructor.getId()}-${this.__instanceId} just updated`);
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

    // ==============
    // Global actions
    // ==============

    registerGlobalAction(name, callback) {
        callback.broadcasterInstanceId = this.__instanceId;
        registerAction(this.constructor.getId(), name, callback);
        this.__actionNames.add(name);
    }
}