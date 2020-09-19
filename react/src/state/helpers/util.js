function some(iterable, predicate) {
    if (Array.isArray(iterable)) {
        return iterable.some(predicate);
    }

    for (const item of iterable) {
        if (predicate(item)) {
            return true;
        }
    }
    return false;
}

/**
 * Recursively checks if the current broadcaster is present in any of its listeners.
 * Returns true if any sub-listener links to the broadcaster defined by `id`.
 */
function isPresentInListeners(broadcaster, id) {
    return broadcaster.constructor.getId() === id || some(broadcaster.__listeners, listener => isPresentInListeners(listener, id));
}

export {
    isPresentInListeners,
};