import { useEffect, useState } from 'react';

export default function(stateTransmitterType, args) {
    const [transmitter, setTransmitter] = useState(new stateTransmitterType(args));
    useEffect(() => {
        return () => {
            transmitter.destroy();
            setTransmitter(null);
        };
    }, []);

    return transmitter;
}