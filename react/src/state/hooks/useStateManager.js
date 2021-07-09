import { useEffect, useState } from 'react';

export default function(creator, dependencies = []) {
    const [manager, setManager] = useState(creator);

    // Create a new state manager if any of the dependencies change.
    useEffect(() => {
        return () => {
            manager.destroy();
            const newManager = typeof creator === 'function' ? creator() : new creator();
            setManager(newManager);
        };
    }, dependencies);

    // Tear down the state manager on unmount.
    useEffect(() => {
        return () => {
            manager.destroy();
            setManager(null);
        };
    }, []);

    return manager;
}