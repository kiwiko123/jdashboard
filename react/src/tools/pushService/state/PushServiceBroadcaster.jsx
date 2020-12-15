import { get } from 'lodash';
import Broadcaster from '../../../state/Broadcaster';
import PushServiceSessionManager from '../private/PushServiceSessionManager';

const WEB_SOCKET_TEMPLATE = {
    close: () => {},
};

export default class PushServiceBroadcaster extends Broadcaster {
    constructor({ serviceId }) {
        super();
        this._webSocket = WEB_SOCKET_TEMPLATE;
        this.serviceId = serviceId;
        this.currentUser = null;
        this.disable();

        this.registerMethod(this.push);
        this.registerMethod(this.onPushReceived);
    }

    receive(state, id) {
        if (id === 'UserDataBroadcaster') {
            if (state.id) {
                this.currentUser = { ...state };
                this._webSocket = PushServiceSessionManager.getSession(state.id, {
                    onOpen: this._onOpen.bind(this),
                    onClose: this._onClose.bind(this),
                    onMessage: this._onMessage.bind(this),
                    onError: this._onError.bind(this),
                });
                this.enable();
            }
        }
    }

    push(payload = {}) {
        const data = JSON.stringify({
            serviceId: this.serviceId,
            userId: this.currentUser.id,
            ...payload,
        });
        this._webSocket.send(data);
    }

    /**
     * This function is invoked when a push notification is received from the server.
     * Note that the `data` parameter is a raw string; if a JSON object is expected,
     * it must be converted first.
     *
     * @param data the raw string data received from the server
     */
    onPushReceived(data) {

    }

    onConnectionOpened() {

    }

    destroy() {
        super.destroy();
        this._onClose();
    }

    _onOpen(event) {
        this.push();
        this.onConnectionOpened();
    }

    _onClose(event) {
        if (this.currentUser) {
            PushServiceSessionManager.endSession(this.currentUser.id);
        }
    }

    _onMessage(event) {
        const data = get(event, 'data');
        this.onPushReceived(data);
    }

    _onError(event) {
        // TODO
    }
}