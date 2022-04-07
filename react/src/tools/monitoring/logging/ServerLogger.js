import { get } from 'lodash';
import Request from 'tools/http/Request';
import Logger from './Logger';
import ConsoleLogger from './ConsoleLogger';

const LOG_BY_LEVEl_URL = '/logging/api/log';

export default class ServerLogger extends Logger {
    constructor() {
        super();
        this._consoleLogger = new ConsoleLogger();
    }

    log(level, message, error) {
        const payload = {
            level,
            message,
            error: get(error, 'message'),
            timestamp: new Date(),
        };
        Request.to(LOG_BY_LEVEl_URL)
            .body(payload)
            .put()
            .then(() => {
                this._consoleLogger.log(level, message, error);
            })
            .catch((error) => {
                this._consoleLogger.error('Error logging to server', error);
            });
    }
}