import React, { useCallback, useState } from 'react';

/**
 * Like useState, but provides behavior similar to a class-based component's setState method.
 * The entire state object is returned via the `state` variable.
 * Calling the setter will one-dimensionally merge the new state with the existing state object.
 *
 * @return the full state object
 */
export default function(obj) {
    const [state, setState] = useState(obj || {});
    const mergeState = useCallback((newState) => {
        setState({
            ...state,
            ...newState,
        });
    }, [state]);

    return [state, mergeState];
}