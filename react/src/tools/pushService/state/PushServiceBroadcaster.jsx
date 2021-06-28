import { get } from 'lodash';
import Broadcaster from '../../../state/Broadcaster';
import PushServiceSessionManager from '../private/PushServiceSessionManager';
import logger from '../../../common/js/logging';

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
    }

    receive(state, id) {
        if (id === 'UserDataBroadcaster') {
            if (state.id) {
                this.userId = state.id;
                return; // TESTING PushServiceStateTransmitter
                this._webSocket = PushServiceSessionManager.getSession(state.id, this.serviceId, {
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
        logger.debug(`${this.constructor.getId()}-${this.serviceId} received push from server: ${data}`);
    }

    onConnectionOpened() {
        // TODO consider removing this, because it's not necessarily "on opened" with a single web socket for all push services
    }

    destroy() {
        super.destroy();
        this._onClose();
    }

    _onOpen(event) {
        // Send an initial, empty push to establish this service's connection with the server.
        PushServiceSessionManager.processUserServices(this.userId, serviceId => this.push({ serviceId }));
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