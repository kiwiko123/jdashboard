
// TODO use configuration file for host
const PUSH_SERVICE_URL = 'ws://localhost:8080/push';
const SESSIONS = new Map();

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

function getSession(userId, { onOpen, onClose, onMessage, onError } = {}) {
    if (!SESSIONS.has(userId)) {
        const data = {
            webSocket: makeWebSocket({ onOpen, onClose, onMessage, onError }),
            handlers: { onOpen, onClose, onMessage, onError },
        };
        SESSIONS.set(userId, data);
    }
    return SESSIONS.get(userId).webSocket;
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
        this.endSession(userId);
    }
}

export default {
    getSession,
    endSession,
    purge,
};