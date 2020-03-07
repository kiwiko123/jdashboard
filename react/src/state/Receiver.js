import { throttle } from 'lodash';
import { logger } from '../common/js/logs';

/**
 * State management class that receives data from a Broadcaster and passes it along to a component.
 * A Receiver is meant to transform data from a Broadcaster into a format that a component can directly ingest.
 * A Receiver should be passed into a ReceivingElement;
 * calls to setState will pass the receiver's state through the ReceivingElement and into the child component as props.
 * Extend this class and invoke setState to propagate data through to a component.
 * In general, the only method that should be overridden is `funnel`.
 */
export default class Receiver {

    static getId() {
        return this.name;
    }

    // "Override" this to control the minimum duration, in milliseconds, between re-renders.
    // Increasing this value may reduce
    reRenderMillis = 50;

    constructor(broadcasters = []) {
        this.state = {};
        this._funnelState = {};
        this.updateReceivingElement = null;
        this.lastUpdatedMillis = new Date().getTime();
        this.broadcasters = broadcasters;
        this.broadcasters.forEach(broadcaster => broadcaster.register(this));
    }

    /**
     * Merges newState with this.state, akin to a React class-based component's setState method.
     * Invoking this can trigger a ReceivingElement to re-render with the updated state.
     */
    setState(newState) {
        Object.assign(this.state, newState);
        if (this.canUpdate()) {
            this.updateReceivingElement();
        }
    }

    /**
     * All broadcasters to which this receiver is registered will trigger this method.
     * Whenever a linked broadcaster calls its setState, its state will be passed into this method.
     * The broadcaster that called setState can be identified through the sourceId argument,
     * which can be compared with a derived class of Broadcaster's `getId()` static method.
     */
    funnel(state, sourceId) {
        logger.warn("No implementation provided for funnel()");
    }

    /**
     * A broadcaster will directly invoke this method when it calls setState.
     * Do not override this.
     */
    setFunnel({ state = {}, sourceId }) {
        Object.assign(this._funnelState, state);
        this.funnel(this._funnelState, sourceId);
    }

    /**
     * Returns this receiver's state.
     * A ReceivingElement will invoke this to retrieve state and pass it as props to a component.
     * Do not override this.
     */
    getState() {
        return { ...this.state };
    }

    /**
     * ReceivingElement calls this on its first render.
     * The updater argument is a function that invokes setState in the ReceivingElement, which will trigger a re-render.
     * This receiver will invoke the updater when it calls setState.
     * Do not override this.
     */
    setUpdater(updater) {
        this.updateReceivingElement = throttle(updater, this.reRenderMillis, { leading: false, trailing: true });
    }

    /**
     * Determines if the ReceivingElement can re-render when this receiver's state changes.
     * By default, this will happen if an updater function has been provided (via setUpdater).
     * Note that the act of this method returning true does not guarantee a re-render,
     * which also has a minimum wait duration represented by `this.reRenderMillis`.
     */
    canUpdate() {
        return Boolean(this.updateReceivingElement);
    }
}