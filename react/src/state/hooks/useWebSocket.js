import { useEffect, useRef } from 'react';

function useEventListenerEffect(webSocket, eventName, action) {
    useEffect(() => {
        if (!action) {
            return;
        }

        webSocket.addEventListener(eventName, action);
        return () => {
            webSocket.removeEventListener(eventName, action);
        };
    }, [webSocket, eventName, action]);
}

/**
 * Create a stateful WebSocket object.
 *
 * @param url the url to which to connect
 * @param { onOpen } a function that gets invoked once when a connection is established
 * @param { onClose } a function that gets invoked when the connection has been successfully closed
 * @param { onMessage } a function that gets invoked when is received through the web socket
 * @param { onError } a function that gets invoked when the connection has been closed because of an error
 * @return a WebSocket object
 * @see WebSocket
 */
export default function(url, { onOpen, onClose, onMessage, onError, dependencies } = {}) {
    const webSocketRef = useRef(new WebSocket(url));
    const webSocket = webSocketRef.current;

    // Close the socket on unmount.
    useEffect(() => () => { webSocket.close(); }, dependencies);

    useEffect(() => {
        // Avoid creating a new WebSocket here on the initial render.
        if (url !== webSocket.url) {
            webSocketRef.current = new WebSocket(url);
        }
    }, [url, ...(dependencies || [])]);

    useEventListenerEffect(webSocket, 'open', onOpen);
    useEventListenerEffect(webSocket, 'close', onClose);
    useEventListenerEffect(webSocket, 'message', onMessage);
    useEventListenerEffect(webSocket, 'error', onError);

    return webSocket;
}