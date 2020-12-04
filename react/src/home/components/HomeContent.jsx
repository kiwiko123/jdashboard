import React from 'react';
import IconButton from '../../common/components/IconButton';
import { useWebSocket, usePushService } from '../../state/hooks';

import './styles/HomeContent.css';

const HomeContent = () => {
    const { pushToServer } = usePushService('chatroom', 1, {
        receivePush: ({ data }) => console.log(`Client received push from server: "${data}"`),
    });
    const pressButton = () => pushToServer({
        userId: 1,
        recipientUserId: 2,
        message: 'Client is pushing to server!',
    });

    return (
        <div className="HomeContent">
            <span>
                Hi! Welcome to Jdashboard.
            </span>
            <IconButton
                onClick={pressButton}
            >
                Test
            </IconButton>
        </div>
    );
};

export default HomeContent;