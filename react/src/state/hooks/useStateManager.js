import { useEffect, useRef } from 'react';

export default function(createStateManager) {
    const ref = useRef(null);
    useEffect(() => {
        return () => {
            ref.current.transmitter.destroy();
            ref.current = null;
        };
    }, []);

    if (!ref.current) {
        ref.current = createStateManager();
    }

    return ref.current;
}