import { useEffect, useState } from 'react';

function useBroadcaster(broadcasterType) {
    const [broadcaster, setBroadcaster] = useState(new broadcasterType());
    useEffect(() => {
        if (!broadcaster) {
            setBroadcaster(new broadcasterType());
        }

        return () => {
            broadcaster.destroy();
            setBroadcaster(null);
        };
    }, [broadcasterType]);

    return broadcaster;
}

export {
    useBroadcaster,
};