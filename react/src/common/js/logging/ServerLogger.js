import { get } from 'lodash';
import Request from '../Request';
import Logger from './Logger';
import ConsoleLogger from './ConsoleLogger';

const LOG_BY_LEVEl_URL = '/logging/api/log/by-level';

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
        };
        Request.to(LOG_BY_LEVEl_URL)
            .withBody(payload)
            .post()
            .then(() => {
                this._consoleLogger.log(level, message, error);
            });
    }
}