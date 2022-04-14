import logger from 'tools/monitoring/logging';

const STATE_TRANSMITTERS_BY_TAG = new Map();

function register(stateTransmitter) {
    const transmitters = STATE_TRANSMITTERS_BY_TAG.has(stateTransmitter.tag)
        ? STATE_TRANSMITTERS_BY_TAG.get(stateTransmitter.tag)
        : new Set([]);
    transmitters.add(stateTransmitter);
    STATE_TRANSMITTERS_BY_TAG.set(stateTransmitter.tag, transmitters);
    logger.debug(`Registered state transmitter ${stateTransmitter.id}`);
}

function deregister(stateTransmitter) {
    if (!STATE_TRANSMITTERS_BY_TAG.has(stateTransmitter.tag)) {
        return;
    }
    const transmitters = STATE_TRANSMITTERS_BY_TAG.get(stateTransmitter.tag);
    transmitters.delete(stateTransmitter);
    STATE_TRANSMITTERS_BY_TAG.set(stateTransmitter.tag, transmitters);
    logger.debug(`De-registered state transmitter ${stateTransmitter.id}`);
}

function get(tag) {
    return STATE_TRANSMITTERS_BY_TAG.has(tag)
        ? STATE_TRANSMITTERS_BY_TAG.get(tag)
        : new Set([]);
}

function sendState(sourceTag, sourceState, recipientTag, metadata) {
    get(recipientTag).forEach(transmitter => routeState(transmitter, sourceTag, sourceState, metadata));
}

function routeState(transmitter, sourceTag, sourceState, metadata) {
    const receiveSpecificTransmitterFunctionName = `receive${sourceTag}`;
    let receiveSpecificTransmitter = transmitter[receiveSpecificTransmitterFunctionName];
    if (receiveSpecificTransmitter) {
        logger.debug(`Routing state to ${transmitter.tag}.${receiveSpecificTransmitterFunctionName}`);
        receiveSpecificTransmitter.bind(transmitter)(sourceState, metadata);
    } else {
        logger.debug(`No specific receive method found matching "${receiveSpecificTransmitterFunctionName}"; routing to receive`);
        transmitter.receiveState(sourceTag, sourceState, metadata);
    }
}

export default {
    register,
    deregister,
    get,
    sendState,
};