import React from 'react';
import IconButton from '../../common/components/IconButton';
import { GLOBAL_addMessage } from '../../dashboard/status/StatusPlaneStateManager';

import './HomeContent.css';

const HomeContent = ({ push }) => {
    const onPush = () => {
        push({ recipientUserId: 1, message: 'Hello!' });
        GLOBAL_addMessage({
            id: 'test1',
            message: 'Hi! This is a test.',
        });
    };

    return (
        <div className="HomeContent">
            <span>
                Hi! Welcome to Jdashboard.
            </span>
            <IconButton
                onClick={onPush}
            >
                Press me!
            </IconButton>
        </div>
    );
};

export default HomeContent;