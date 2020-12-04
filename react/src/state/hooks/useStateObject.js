import React, { useCallback, useState } from 'react';

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