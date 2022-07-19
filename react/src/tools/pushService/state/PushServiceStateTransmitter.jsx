import { get } from 'lodash';
import logger from 'tools/monitoring/logging';
import StateManager from 'state/StateManager';
import getCurrentUser from 'tools/users/util/getCurrentUser';
import PushServiceSessionManager from '../private/PushServiceSessionManager';

const WEB_SOCKET_TEMPLATE = {
    close: () => {},
};
let GLOBAL_ID = 0;

export default class PushServiceStateTransmitter extends StateManager {
    constructor(serviceId, { userId } = {}) {
        super();
        this.serviceId = serviceId;
        this.userId = userId;
        this.id = `${this.constructor.name}-${this.serviceId}-${GLOBAL_ID++}`;
        this._webSocket = WEB_SOCKET_TEMPLATE;
        this._webSocketSessionId = null; // Set after the web socket opens its connection.

        if (userId) {
            this._establishConnection();
        } else {
            getCurrentUser().then((user) => {
                if (!user) {
                    logger.error(`Unable to open Push Service connection because the current user cannot be determined: ${this.id}`);
                    return;
                }
                this.userId = user.userId;
                this._establishConnection();
            });
        }
    }

    pushToServer(payload = {}) {
        const data = JSON.stringify({
            ...payload,
            // Spread the payload first so that it can't spoof the required connection fields.
            sessionId: this._webSocketSessionId,
            serviceId: this.serviceId,
            userId: this.userId,
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
        logger.debug(`${this.id} received push from server: ${data}`);
    }

    onConnectionOpened() {

    }

    _establishConnection() {
        this._webSocket = PushServiceSessionManager.getSession(this.userId, this.serviceId, {
            onOpen: this._onOpen.bind(this),
            onClose: this._onClose.bind(this),
            onMessage: this._onMessage.bind(this),
            onError: this._onError.bind(this),
        });
    }

    _onOpen(event) {
        // Send an initial, empty push to establish this service's connection with the server.
        this.pushToServer({ __initial: true });
        this.onConnectionOpened();
    }

    _onClose(event) {
        if (this.userId) {
            PushServiceSessionManager.endSession(this.userId);
        }
        this._webSocketSessionId = null;
    }

    _onMessage(event) {
        const data = get(event, 'data');
        if (!this._webSocketSessionId) {
            this._confirmConnection(data);
            return;
        }

        this.onPushReceived(data);
    }

    _onError(event) {
        // TODO
    }

    tearDown() {
        super.tearDown();
        this._onClose();
    }

    _confirmConnection(rawJsonData) {
        const payload = JSON.parse(rawJsonData);
        if (!payload.sessionId) {
            logger.error('Expected to but did not receive push service connection confirmation');
        }
        this._webSocketSessionId = payload.sessionId;
    }
}