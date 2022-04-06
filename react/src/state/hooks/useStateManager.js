import { useEffect, useRef } from 'react';

export default function(createStateManager) {
    const ref = useRef(null);
    if (!ref.current) {
        ref.current = createStateManager();
    }

    useEffect(() => {
        return () => {
            ref.current.tearDown();
            ref.current = null;
        };
    }, []);

    return ref.current;
}