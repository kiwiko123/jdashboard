import { useEffect, useState } from 'react';

export default function(broadcasterType) {
    const [broadcaster] = useState(new broadcasterType());
    useEffect(() => () => broadcaster.destroy(), []);

    return broadcaster;
}