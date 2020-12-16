import { useCallback } from 'react';

export default function(callback, dependencies = []) {
    return useCallback((event) => {
        if (callback && event.keyCode === 13) { // Enter/Return
            callback();
        }
    }, dependencies);
}