
// TODO use configuration file for host
const PUSH_SERVICE_URL = 'ws://localhost:8080/push';
const SESSIONS = new Map();
const PENDING_SERVICE_IDS = new Set();

function addEventListener(webSocket, name, handler) {
    if (handler) {
        webSocket.addEventListener(name, handler);
    }
}

function removeEventListener(webSocket, name, handler) {
    if (handler) {
        webSocket.removeEventListener(name, handler);
    }
}

function makeWebSocket({ onOpen, onClose, onMessage, onError }) {
    const webSocket = new WebSocket(PUSH_SERVICE_URL);
    addEventListener(webSocket, 'open', onOpen);
    addEventListener(webSocket, 'close', onClose);
    addEventListener(webSocket, 'message', onMessage);
    addEventListener(webSocket, 'error', onError);
    return webSocket;
}

function processUserServices(userId, fn) {
    if (!SESSIONS.has(userId)) {
        return;
    }
    const data = SESSIONS.get(userId);
    data.serviceIds.forEach(fn);
    data.serviceIds.clear();
}

function getSession(userId, serviceId, { onOpen, onClose, onMessage, onError } = {}) {
    let data;
    if (SESSIONS.has(userId)) {
        data = SESSIONS.get(userId);
        data.serviceIds.add(serviceId);
    } else {
        data = {
            webSocket: makeWebSocket({ onOpen, onClose, onMessage, onError }),
            handlers: { onOpen, onClose, onMessage, onError },
            serviceIds: new Set([serviceId]),
        };

    }

    SESSIONS.set(userId, data);
    return data.webSocket;
 }

function endSession(userId) {
    if (!SESSIONS.has(userId)) {
        return;
    }
    const { webSocket, handlers } = SESSIONS.get(userId);
    webSocket.close();
    removeEventListener(webSocket, 'open', handlers.onOpen);
    removeEventListener(webSocket, 'close', handlers.onClose);
    removeEventListener(webSocket, 'message', handlers.onMessage);
    removeEventListener(webSocket, 'error', handlers.onError);
    SESSIONS.delete(userId);
}

function purge() {
    for (const userId of SESSIONS.keys()) {
        endSession(userId);
    }
}

export default {
    processUserServices,
    getSession,
    endSession,
    purge,
};