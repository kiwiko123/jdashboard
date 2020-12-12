import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';

const PUSH_SERVICE_URL = 'ws://localhost:8080/push';
const WEB_SOCKET_TEMPLATE = {
    close: () => {},
};

function makeWebSocket({ onOpen, onClose, onMessage, onError }) {
    const webSocket = new WebSocket(PUSH_SERVICE_URL);
    webSocket.addEventListener('open', onOpen);
    webSocket.addEventListener('close', onClose);
    webSocket.addEventListener('message', onMessage);
    webSocket.addEventListener('error', onError);
    return webSocket;
}

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
            if (state.userId) {
                this.currentUser = { ...state };
                this._webSocket = makeWebSocket({
                    onOpen: this._onOpen.bind(this),
                    onClose: this._onClose.bind(this),
                    onMessage: this._onMessage.bind(this),
                    onError: this._onError.bind(this),
                });
                this.enable();
            }
        }
    }

    push(payload) {
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

    close() {
        this._webSocket.close();
        this._webSocket.removeEventListener('open', this._onOpen);
        this._webSocket.removeEventListener('close', this._onClose);
        this._webSocket.removeEventListener('message', this._onMessage);
        this._webSocket.removeEventListener('error', this._onError);
    }

    destroy() {
        super.destroy();
        this.close();
    }

    _onOpen(event) {
        this.push({});
        this.onConnectionOpened();
    }

    _onClose(event) {
        this.close();
    }

    _onMessage(event) {
        const data = get(event, 'data');
        this.onPushReceived(data);
    }

    _onError(event) {
        // TODO
    }
}