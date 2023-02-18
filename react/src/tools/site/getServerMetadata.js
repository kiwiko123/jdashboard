import GlobalStorage from 'tools/storage/GlobalStorage';

const STORAGE_KEY = '__JDASHBOARD_INTERNAL:client_sessions.server_metadata';

export default function getServerMetadata() {
    const data = GlobalStorage.get(STORAGE_KEY);
    if (!data) {
        throw new Error('Unable to retrieve server metadata.');
    }
    return data;
}