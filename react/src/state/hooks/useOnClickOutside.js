import { useCallback, useEffect, useRef } from 'react';

export default function(onClickOutside) {
    const ref = useRef(null);
    const handleClickOutside = useCallback((event) => {
        if (ref.current && !ref.current.contains(event.target)) {
            onClickOutside();
        }
    }, [onClickOutside]);
    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [onClickOutside, ref]);

    return ref;
}