import React from 'react';
import PropTypes from 'prop-types';
import messagePropType from './propTypes/messagePropType';
import MessageBubble from './MessageBubble';

import './styles/MessageContainer.css';

const MessageContainer = ({
    messages,
}) => {
    const messageElements = messages.map(message => (
        <MessageBubble
            {...message}
            key={message.id}
        />
    ));
    return (
        <div className="MessageContainer">
            {messageElements}
        </div>
    );
}

MessageContainer.propTypes = {
    messages: PropTypes.arrayOf(PropTypes.shape(MessageBubble.propTypes)),
};

MessageContainer.defaultProps = {
    messages: [],
};

export default MessageContainer;