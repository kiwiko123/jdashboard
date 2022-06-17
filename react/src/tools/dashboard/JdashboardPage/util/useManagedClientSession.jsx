import { useEffect } from 'react';
import ClientSessionManager from 'tools/clientSessions/ClientSessionManager';

export default function useManagedClientSession() {
    useEffect(() => {
        const clientSessionManager = new ClientSessionManager();
        clientSessionManager.createNewSession();

        const endSession = clientSessionManager.endSession.bind(clientSessionManager);
        window.addEventListener('beforeunload', endSession);

        return () => {
            window.removeEventListener('beforeunload', endSession);
        };
    }, []);
}