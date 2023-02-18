import Request from 'tools/http/Request';
import GlobalStorage from 'tools/storage/GlobalStorage';
import logger from 'common/js/logging';

const SERVER_METADATA_STORAGE_KEY = '__JDASHBOARD_INTERNAL:client_sessions.server_metadata';

function setServerMetadata(response) {
    const serverMetadata = {
        userId: response.userId,
        permissions: new Set(response.permissions),
    };
    GlobalStorage.set(SERVER_METADATA_STORAGE_KEY, serverMetadata);
}

export default class ClientSessionManager {
    constructor() {
        this._clientSessionUuid = null;
    }

    createNewSession() {
        Request.to('/client-sessions/api')
            .authenticated()
            .post()
            .then((response) => {
                this._clientSessionUuid = response.uuid;
                setServerMetadata(response);
            });
    }

    endSession() {
        if (!this._clientSessionUuid) {
            logger.warn('No client session ID present');
            return;
        }

        GlobalStorage.remove(SERVER_METADATA_STORAGE_KEY);

        Request.to(`/client-sessions/api/${this._clientSessionUuid}/end`)
            .post()
            .catch((e) => {
                logger.error(`Error ending client session UUID ${this._clientSessionUuid}`, e);
            });
    }
}