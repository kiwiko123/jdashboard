

export default class Logger {

    log(level, message, error) {
        throw new Error('No implementation for log()');
    }

    error(message, error) {
        this.log('error', message, error);
    }

    warn(message, error) {
        this.log('warn', message, error);
    }

    info(message, error) {
        this.log('info', message, error);
    }

    debug(message, error) {
        this.log('debug', message, error);
    }

}