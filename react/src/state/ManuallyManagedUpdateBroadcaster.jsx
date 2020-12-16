import { debounce } from 'lodash';
import Broadcaster from './DefaultBroadcaster';

export default class ManuallyManagedUpdateBroadcaster extends Broadcaster {

    constructor() {
        this.__lastUpdatedTime = new Date();
    }

    canUpdate() {
        if (!super.canUpdate()) {
            return false;
        }

        const now = new Date();
        const remainingDelayMillis = now - this.__lastUpdatedTime;
        if (remainingDelayMillis >= this.reRenderMillis) {
            this.__lastUpdatedTime = now;
            return true;
        }

        setTimeout(() => this.setState({}), remainingDelayMillis);
        return false;
    }
}