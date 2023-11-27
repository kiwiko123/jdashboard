import React, { useReducer } from 'react';
import IconButton from '../../common/components/IconButton';
import { GLOBAL_addMessage } from '../../dashboard/status/StatusPlaneStateManager';

import './HomeContent.css';

const HomeContent = ({ push }) => {
    const [counter, incrementCounter] = useReducer(i => i + 1, 0);

    const onPush = () => {
        push({ recipientUserId: 1, message: 'Hello!' });
        GLOBAL_addMessage({
            id: `${counter}`,
            message: `(${counter}) Hi! This is a test.`,
        });
        incrementCounter();
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