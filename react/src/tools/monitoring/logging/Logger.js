

export default class Logger {

    log(level, message, error) {
        throw new Error('No implementation for log()');
    }

    error(message, error) {
        this._writeLog('error', message, error);
    }

    warn(message, error) {
        this._writeLog('warn', message, error);
    }

    info(message, error) {
        this._writeLog('info', message, error);
    }

    debug(message, error) {
        this._writeLog('debug', message, error);
    }

    _writeLog(level, message, error) {
        let messageText = message;
        if (typeof message === 'function') {
            messageText = message();
        }
        this.log(level, messageText, error);
    }
}