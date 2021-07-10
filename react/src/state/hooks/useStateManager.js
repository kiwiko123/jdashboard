import { useEffect, useRef } from 'react';
import { isFunction } from 'lodash';

export default function(createStateManager) {
    const ref = useRef(null);
    useEffect(() => {
        return () => {
            ref.current.transmitter.destroy();
        };
    }, []);

    if (!ref.current) {
        ref.current = createStateManager();
    }

    return ref.current;
}