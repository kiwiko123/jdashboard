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
        Request.to(`/client-sessions/api/${this._clientSessionUuid}/end`)
            .post()
            .then((response) => {
                logger.debug(`Successfully ended client session ${response.id}`);
            })
            .catch((e) => {
                logger.error(`Error ending client session UUID ${this._clientSessionUuid}`, e);
            })
    }
}