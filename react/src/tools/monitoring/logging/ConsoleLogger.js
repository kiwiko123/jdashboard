import { isNil } from 'lodash';
import Logger from './Logger';

function log(logFunction, level, message, error = null) {
    const outputMessage = `[${level.toUpperCase()}] ${message}`;
    if (isNil(error)) {
        logFunction(outputMessage);
    } else {
        logFunction(outputMessage, error);
    }
}

export default class ConsoleLogger extends Logger {

    log(level, message, error) {
        let logFunction;
        switch (level.toLowerCase()) {
            case 'error':
                logFunction = console.error;
                break;
            case 'warn':
                logFunction = console.warn;
                break;
            case 'info':
                logFunction = console.info;
                break;
            case 'debug':
                logFunction = console.debug;
                break;
            default:
                throw new Error(`Unknown level ${level}`);
        }
        log(logFunction, level, message, error);
    }
}