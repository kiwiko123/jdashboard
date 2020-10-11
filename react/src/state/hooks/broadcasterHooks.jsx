import { useState } from 'react';

function useBroadcaster(broadcasterType) {
    const [broadcaster] = useState(new broadcasterType());
    useEffect(() => () => broadcaster.destroy(), [broadcaster]);

    return broadcaster;
}

export {
    useBroadcaster,
};