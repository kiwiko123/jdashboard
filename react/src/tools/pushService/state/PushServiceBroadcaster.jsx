import { get } from 'lodash';
import Broadcaster from '../../../state/Broadcaster';
import PushServiceSessionManager from '../private/PushServiceSessionManager';

const WEB_SOCKET_TEMPLATE = {
    close: () => {},
};

export default class PushServiceBroadcaster extends Broadcaster {
    constructor({ serviceId, userId }) {
        super();
        this._webSocket = WEB_SOCKET_TEMPLATE;
        this.serviceId = serviceId;
        this.userId = userId;
        this.disable();

        this.registerMethod(this.push);
        this.registerMethod(this.onPushReceived);
    }

    receive(state, id) {
        if (id === 'UserDataBroadcaster') {
            if (state.id) {
                this.userId = state.id;
                this._webSocket = PushServiceSessionManager.getSession(state.id, {
                    onOpen: this._onOpen.bind(this),
                    onClose: this._onClose.bind(this),
                    onMessage: this._onMessage.bind(this),
                    onError: this._onError.bind(this),
                });
                this.enable();
                this._attemptToEstablishConnection();
            }
        }
    }

    push(payload = {}) {
        const data = JSON.stringify({
            serviceId: this.serviceId,
            userId: this.userId,
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
        // NOTE: this does not work when there is more than one active push service, because the second one never fires the initial event, due to a cached WebSocket.
        this.push(); // Send an initial, empty push to establish this service's connection with the server.
        this.onConnectionOpened();
    }

    _onClose(event) {
        if (this.userId) {
            PushServiceSessionManager.endSession(this.userId);
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