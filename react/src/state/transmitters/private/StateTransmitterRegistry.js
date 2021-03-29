const STATE_TRANSMITTERS_BY_TAG = new Map();

function register(stateTransmitter) {
    const transmitters = STATE_TRANSMITTERS_BY_TAG.has(stateTransmitter.tag)
        ? STATE_TRANSMITTERS_BY_TAG.get(stateTransmitter.tag)
        : new Set([]);
    transmitters.add(stateTransmitter);
    STATE_TRANSMITTERS_BY_TAG.set(stateTransmitter.tag, transmitters);
}

function deregister(stateTransmitter) {
    if (!STATE_TRANSMITTERS_BY_TAG.has(stateTransmitter.tag)) {
        return;
    }
    const transmitters = STATE_TRANSMITTERS_BY_TAG.get(stateTransmitter.tag);
    transmitters.delete(stateTransmitter);
    STATE_TRANSMITTERS_BY_TAG.set(stateTransmitter.tag, transmitters);
}

function get(tag) {
    return STATE_TRANSMITTERS_BY_TAG.has(tag)
        ? STATE_TRANSMITTERS_BY_TAG.get(tag)
        : new Set([]);
}

function sendState(sourceTag, sourceState, recipientTag, { event }) {
    get(recipientTag)
        .forEach(transmitter => transmitter.receiveState(sourceState, { tag: sourceTag, event }));
}

export default {
    register,
    deregister,
    get,
    sendState,
};