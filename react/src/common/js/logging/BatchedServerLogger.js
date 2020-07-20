import { get } from 'lodash';
import Request from '../Request';
import Logger from './Logger';
import ConsoleLogger from './ConsoleLogger';

const LOG_BY_LEVEl_BATCHED_URL = '/logging/api/log/batched/by-level';
const BATCH_SIZE = 5;
const MINIMUM_STALE_SECONDS = 60;

let LOGS = [];
let PENDING_LOGS = [];

export default class BatchedServerLogger extends Logger {
    constructor() {
        super();
        this._consoleLogger = new ConsoleLogger();
        this._batchSize = BATCH_SIZE;
    }

    log(level, message, error) {
        const nextLog = {
            level,
            message,
            error: get(error, 'message'),
            timestamp: new Date(),
        };
        LOGS.push(nextLog);
        this._consoleLogger.log(level, message, error);

        if (this._shouldLogToServer()) {
            this._logToServer();
        }
    }

    _shouldLogToServer() {
        // If there are more logs than the maximum batch size, log everything to the server.
        if (LOGS.length >= this._batchSize) {
            return true;
        }

        // If the last log is more than 10 seconds old, log everything to the server.
        if (LOGS.length > 0) {
            const now = new Date();
            const oldestLogDate = LOGS[0].timestamp;
            const elapsedMs = now - oldestLogDate;
            const elapsedSeconds = elapsedMs / 1000;
            if (elapsedSeconds >= MINIMUM_STALE_SECONDS) {
                return true;
            }
        }

        // Don't log anything yet.
        return false;
    }

    _logToServer() {
        PENDING_LOGS = Array.from(LOGS);
        LOGS = [];
        const payload = { logs: PENDING_LOGS };
        Request.to(LOG_BY_LEVEl_BATCHED_URL)
            .withBody(payload)
            .post()
            .then(() => {
                PENDING_LOGS = [];
            })
            .catch((error) => {
                this._consoleLogger.error('Failed batch-log to server', error);
            });
    }
}