import Request from 'tools/http/Request';
import logger from 'common/js/logging';

export default class ClientSessionManager {
    constructor() {
        this._clientSessionUuid = null;
    }

    createNewSession() {
        Request.to('/client-sessions/api')
            .post()
            .then((response) => {
                this._clientSessionUuid = response.uuid;
            });
    }

    endSession() {
        if (!this._clientSessionUuid) {
            logger.warn('No client session ID present');
            return;
        }

        Request.to(`/client-sessions/api/${this._clientSessionUuid}/end`)
            .post()
            .catch((e) => {
                logger.error(`Error ending client session UUID ${this._clientSessionUuid}`, e);
            })
    }
}