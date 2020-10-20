import React from 'react';
import PropTypes from 'prop-types';
import MessageContainer from './MessageContainer';
import MessageInput from './MessageInput';
import messagePropType from './propTypes/messagePropType';

const ChatroomContents = ({
    messages, onInputTextChange, currentUserId,
}) => {
    return (
        <div className="ChatroomContents">
            <MessageContainer
                messages={messages}
            />
            <MessageInput
                onTextChange={onInputTextChange}
                currentUserId={currentUserId}
            />
        </div>
    );
};

ChatroomContents.propTypes = {
    messages: PropTypes.arrayOf(messagePropType),
    onInputTextChange: PropTypes.func.isRequired,
    currentUserId: PropTypes.number.isRequired,
};

export default ChatroomContents;