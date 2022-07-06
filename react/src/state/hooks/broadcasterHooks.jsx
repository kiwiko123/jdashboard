// Deprecated.
import { useEffect, useState } from 'react';

function useBroadcaster(broadcasterType, args) {
    const [broadcaster, setBroadcaster] = useState(new broadcasterType(args));
    useEffect(() => {
        if (!broadcaster) {
            setBroadcaster(new broadcasterType());
        }

        return () => {
            broadcaster.destroy();
            setBroadcaster(null);
        };
    }, [broadcasterType, broadcaster]);

    return broadcaster;
}

export {
    useBroadcaster,
};