import logger from '../../common/js/logging';
import { isPresentInListeners } from '../helpers/util';

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
export default class SimpleBroadcaster {

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
        this.__updaters = new Map();
        this.__instanceId = instanceId++;

        this.register = this.register.bind(this);
    }

    setState(newState) {
        Object.assign(this.state, newState);
        this.broadcast();
    }

    receive(state, broadcasterId) {
        logger.warn(`No implementation for receive in ${this.constructor.getId()}`);
    }

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

    getState() {
        return this.state;
    }

    broadcast() {
        const state = this.getState();
        const id = this.constructor.getId();

        this.__listeners.forEach(broadcaster => broadcaster.receive(state, id));
        this.__updaters.forEach(updater => updater());
        logger.debug(`${id}-${this.__instanceId} just updated`);
    }

    registerMethod(method) {
        this.setState({
            [method.name]: method.bind(this),
        });
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
     * Removes the linked ReceivingElement when it unmounts.
     * Do not override this.
     */
    removeUpdater(id) {
        logger.debug(`Removing ReceivingElement ${id}`);
        this.__updaters.delete(id);
    }

    destroy() {
        logger.debug(`Destroyed broadcaster ${this.constructor.getId()}-${this.__instanceId}`);
    }
}