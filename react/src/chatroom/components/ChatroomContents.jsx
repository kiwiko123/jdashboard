import React from 'react';
import PropTypes from 'prop-types';
import MessageContainer from './MessageContainer';
import messagePropType from './propTypes/messagePropType';

const ChatroomContents = ({
    messages, currentUserId, inboxItems,
}) => {
    return (
        <div className="ChatroomContents">
            <MessageContainer
                messages={messages}
            />
        </div>
    );
};

ChatroomContents.propTypes = {
    messages: PropTypes.arrayOf(messagePropType),
    currentUserId: PropTypes.number.isRequired,
};

export default ChatroomContents;