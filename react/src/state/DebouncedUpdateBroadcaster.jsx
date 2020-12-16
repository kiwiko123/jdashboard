import { debounce } from 'lodash';
import Broadcaster from './DefaultBroadcaster';

export default class DebouncedUpdateBroadcaster extends Broadcaster {

    constructor() {
        super();
        this.update = debounce(this.update, this.getReRenderMillis(), { leading: true, trailing: true });
    }

    getReRenderMillis() {
        return this.reRenderMillis;
    }
}