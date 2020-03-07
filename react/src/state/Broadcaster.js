/**
 * State management class that broadcasts data to a Receiver.
 * A broadcaster is meant to abstract business logic away from components.
 * Calls to setState will "funnel" the broadcaster's state into all registered receivers.
 * Receivers can be individually registered to a broadcaster; this automatically happens when constructing a receiver with broadcasters.
 * Receivers can then selectively format the broadcaster's state before passing it along to a component.
 * Extend this class and invoke setState to propagate data through to registered receivers.
 */
export default class Broadcaster {

    static getId() {
        return this.name;
    }

    constructor() {
        this.state = {};
        this.receivers = [];
    }

    /**
     * Merges the input's state with this broadcaster's state.
     * Funnels this.state to each registered receiver.
     */
    setState(newState) {
        Object.assign(this.state, newState);
        this.receivers.forEach(receiver => receiver.setFunnel({
            state: this.state, sourceId: this.constructor.getId(),
        }));
    }

    /**
     * Registers the given receiver with this broadcaster.
     * Immediately funnels this.state to the new receiver.
     */
    register(receiver) {
        this.receivers.push(receiver);
        receiver.setFunnel({ state: this.state, sourceId: this.constructor.getId() });
    }
}