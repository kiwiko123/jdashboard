import { useCallback, useEffect, useRef } from 'react';

export default function(onClickOutside) {
    const ref = useRef(null);
    useEffect(() => {
        document.addEventListener('mousedown', onClickOutside);
        return () => {
            document.removeEventListener('mousedown', onClickOutside);
        };
    }, [onClickOutside]);

    return ref;
}