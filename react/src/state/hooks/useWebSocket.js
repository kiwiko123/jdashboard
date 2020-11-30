import React, { useEffect, useRef } from 'react';

function useEventListenerEffect(webSocket, eventName, action) {
    useEffect(() => {
        if (!action) {
            return;
        }

        webSocket.addEventListener(eventName, action);
        return () => {
            webSocket.removeEventListener(eventName, action);
        };
    }, [action]);
}

export default function(url, { onMessage, onOpen } = {}) {
    const webSocketRef = useRef(new WebSocket(url));
    const webSocket = webSocketRef.current;

    // Close the socket on unmount.
    useEffect(() => () => { webSocket.close(); }, []);

    useEffect(() => {
        // Avoid creating a new WebSocket here on the initial render.
        if (url !== webSocket.url) {
            webSocketRef.current = new WebSocket(url);
        }
    }, [url]);

    useEventListenerEffect(webSocket, 'open', onOpen);
    useEventListenerEffect(webSocket, 'message', onMessage);

    return webSocket;
}