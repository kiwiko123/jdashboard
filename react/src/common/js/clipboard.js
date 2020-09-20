import logger from './logging';

const CLIPBOARD_GRANTED_STATES = new Set(['granted', 'prompt']);

function _writeToClipboard({ text, onWrite = () => {} }) {
    if (!text) {
        return;
    }

    navigator.clipboard.writeText(text)
        .then(() => onWrite())
        .catch((error) => {
            logger.error(`Writing "${text}" to clipboard failed`, error);
        });
}

function _validateClipboardPermission(permissionName) {
    return navigator.permissions.query({ name: permissionName })
        .then((result) => {
           if (!CLIPBOARD_GRANTED_STATES.has(result.state)) {
               logger.error(`Writing to clipboard requires permission; invalid state "${result.state}"`);
           }
           return result;
       });
}

/**
 * Attempts to write the given text to the clipboard.
 */
export function copy(text) {
    _validateClipboardPermission('clipboard-write')
        .then(() => _writeToClipboard({ text }));
}

/**
 * Returns a promise whose resolved value is the pasted text.
 */
export function paste() {
    return _validateClipboardPermission('clipboard-read')
        .then(() => navigator.clipboard.readText());
}

export default {
    copy,
    paste,
};