import { useCallback, useEffect, useRef } from 'react';

export default function(onClickOutside) {
    const ref = useRef(null);
    const handleClick = useCallback(event => {
        if (ref.current && !ref.current.contains(event.target)) {
            onClickOutside();
        }
    }, [onClickOutside]);

    useEffect(() => {
        document.addEventListener('mousedown', handleClick);
        return () => {
            document.removeEventListener('mousedown', handleClick);
        };
    }, [handleClick]);

    return ref;
}