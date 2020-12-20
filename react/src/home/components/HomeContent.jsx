import React from 'react';
import IconButton from '../../common/components/IconButton';

import './HomeContent.css';

const HomeContent = ({ push }) => {
    return (
        <div className="HomeContent">
            <span>
                Hi! Welcome to Jdashboard.
            </span>
            <IconButton
                onClick={() => push({ recipientUserId: 2, message: 'Hello!' })}
            >
                Press me!
            </IconButton>
        </div>
    );
};

export default HomeContent;