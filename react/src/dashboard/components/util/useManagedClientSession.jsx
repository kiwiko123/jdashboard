import { useEffect } from 'react';
import ClientSessionManager from 'tools/clientSessions/ClientSessionManager';

export default function useManagedClientSession() {
    useEffect(() => {
        const clientSessionManager = new ClientSessionManager();
        clientSessionManager.createNewSession();
        return () => {
            clientSessionManager.endSession();
        };
    }, []);
}