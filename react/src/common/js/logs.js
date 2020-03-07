import { isNil } from 'lodash';

function log(logFunction, level, message, error = null) {
    const outputMessage = `[${level.toUpperCase()}] ${message}`;
    if (isNil(error)) {
        logFunction(outputMessage);
    } else {
        logFunction(outputMessage, error);
    }
}

export class ConsoleLogger {

    error(message, error) {
        log(console.error, 'error', message, error);
    }

    warn(message, error) {
        log(console.warn, 'warn', message, error);
    }

    info(message, error) {
        log(console.info, 'info', message, error);
    }

    debug(message, error) {
        log(console.debug, 'debug', message, error);
    }

    log(message, error) {
        this.info(message, error);
    }
}

export const logger = new ConsoleLogger();