import { useEffect, useRef } from 'react';

function bindFunction(fn, callback) {
    return (args) => {
        fn(args);
        callback(args);
    };
}

const useFunctionBinder = (fn, callback) => {
    const ref = useRef(bindFunction(fn, callback));
    useEffect(() => {
        ref.current = bindFunction(fn, callback);
    }, [fn, callback]);

    return ref.current;
}

export default useFunctionBinder;
