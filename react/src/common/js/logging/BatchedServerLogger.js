import { get } from 'lodash';
import Request from '../Request';
import Logger from './Logger';
import ConsoleLogger from './ConsoleLogger';

const LOG_BY_LEVEl_BATCHED_URL = '/logging/api/log/batched/by-level';
const BATCH_SIZE = 10;
const MINIMUM_STALE_SECONDS = 10;

export default class BatchedServerLogger extends Logger {
    constructor() {
        super();
        this._consoleLogger = new ConsoleLogger();
        this._logs = [];
        this._pendingLogs = [];
        this._batchSize = BATCH_SIZE;
    }

    log(level, message, error) {
        const nextLog = {
            level,
            message,
            error: get(error, 'message'),
            timestamp: new Date(),
        };
        this._logs.push(nextLog);
        this._consoleLogger.log(level, message, error);

        if (this._shouldLogToServer()) {
            this._logToServer();
        }
    }

    _shouldLogToServer() {
        // If there are more logs than the maximum batch size, log everything to the server.
        if (this._logs.length >= this._batchSize) {
            return true;
        }

        // If the last log is more than 10 seconds old, log everything to the server.
        if (this._logs.length > 0) {
            const now = new Date();
            const mostRecentLogDate = this._logs[this._logs.length - 1].timestamp;
            const elapsedMs = now - mostRecentLogDate;
            const elapsedSeconds = elapsedMs / 1000;
            if (elapsedSeconds >= MINIMUM_STALE_SECONDS) {
                return true;
            }
        }

        // Don't log anything yet.
        return false;
    }

    _logToServer() {
        this._pendingLogs = Array.from(this._logs);
        this._logs = [];
        const payload = { logs: this._pendingLogs };
        Request.to(LOG_BY_LEVEl_BATCHED_URL)
            .withBody(payload)
            .post()
            .then(() => {
                this._pendingLogs = [];
            });
    }
}