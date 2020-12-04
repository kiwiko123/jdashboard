import React, { useCallback, useEffect } from 'react';
import { get, startsWith } from 'lodash';
import useWebSocket from './useWebSocket';
import { getServerUrl } from '../../common/js/config';

function normalizeMappingUrl(mapping) {
    const normalizedMapping = mapping.startsWith('/') ? mapping.substr(1) : mapping;
    return `ws://localhost:8080/${normalizedMapping}`;
}

function makeWebSocketParams({ receivePush }) {
    let webSocketParams = {};

    if (receivePush) {
        webSocketParams.onMessage = event => receivePush({
            data: get(event, 'data'),
        });
    }

    return webSocketParams;
}

function validateData(data) {
    // TODO
}

export default function(serviceId, userId, { receivePush } = {}) {
    const url = normalizeMappingUrl('/push');
    const webSocketParams = makeWebSocketParams({ receivePush });
    const webSocket = useWebSocket(url, webSocketParams);

    const pushToServer = useCallback((payload) => {
        const data = JSON.stringify({
            serviceId,
            userId,
            ...payload,
        });
        validateData(data);
        webSocket.send(data);
    }, [serviceId, userId]);

    webSocket.onopen = () => {
        pushToServer({ userId });
    };

    return {
        pushToServer,
    };
}