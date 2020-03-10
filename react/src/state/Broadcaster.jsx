import { throttle } from 'lodash';
import { logger } from '../common/js/logs';

/**
 * Recursively checks if the current broadcaster is present in any of its listeners.
 * Returns true if any sub-listener links to the broadcaster defined by `id`.
 */
function isPresentInListeners(broadcaster, id) {
    return broadcaster.constructor.getId() !== id && broadcaster.listeners.some(listener => isPresentInListeners(listener, id));
}

/**
 * State management class that broadcasts data to either component, or other broadcasters.
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

    // "Override" this to control the minimum duration, in milliseconds, between re-renders.
    reRenderMillis = 50;

    /**
     * Constructs a new Broadcaster instance.
     * If a derived broadcaster defines a constructor, always invoke super().
     * The `broadcasters` argument is an array of broadcasters that will each be immediately registered and listening to this.
     */
    constructor(broadcasters = []) {
        this.state = {};
        this.listeners = [];

        this.register = this.register.bind(this);
        this.updaters = new Map();

        broadcasters.forEach(broadcaster => broadcaster.register(this));
    }

    /**
     * One-dimensionally merges the input state with this broadcaster's state.
     * Passes state into listening ReceivingElements and broadcasters.
     * Do not override this.
     */
    setState(newState) {
        Object.assign(this.state, newState);
        if (this.canReRender()) {
            this.updaters.forEach(updater => updater());
        }
        this.listeners.forEach(broadcaster => broadcaster.receive(this.getState(), this.constructor.getId()));
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
     * Determines if the ReceivingElement is eligible to re-render.
     * The act of this method returning true makes no guarantees that the ReceivingElement _will_ re-render;
     * rather, it's a signal that it _can_.
     */
    canReRender() {
        return this.updaters.size > 0;
    }

    /**
     * Return a view of this broadcaster's state.
     * By default, this returns a one-dimensional copy of the state.
     * Returning a reference to the state (i.e., the state itself), _may_ improve performance.
     */
    getState() {
        return { ...this.state };
    }

    /**
     * Registers the given broadcaster with this broadcaster.
     * Immediately funnels this.state to the newly-registered broadcaster.
     * There cannot be any cycles within the listener graph;
     * if one is detected, the broadcaster will not be registered.
     * Do not override this.
     */
    register(broadcaster) {
        if (isPresentInListeners(broadcaster, this.constructor.getId())) {
            logger.error(`Failed to register ${broadcaster.constructor.getId()}; cycle detected in listeners`);
            return;
        }
        this.listeners.push(broadcaster);
        broadcaster.receive(this.getState(), this.constructor.getId());
    }

    /**
     * A ReceivingElement invokes this on its first render.
     * The updater will effectively call setState on the ReceivingElement to induce a re-render.
     * Do not override this.
     */
    setUpdater(updater, id) {
        const updateFunction = throttle(updater, this.reRenderMillis, { leading: false, trailing: true });
        this.updaters.set(id, updateFunction);
    }

    /**
     * Removes the linked ReceivingElement when it unmounts.
     * Do not override this.
     */
    removeUpdater(id) {
        logger.debug(`Removing ReceivingElement ${id}`);
        this.updaters.delete(id);
    }
}