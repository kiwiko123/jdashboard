import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import messagePropType from './propTypes/messagePropType';
import MessageBubble from './MessageBubble';

import './styles/MessageContainer.css';

const MessageContainer = ({
    messages,
}) => {
    const endRef = useRef(null);
    const scrollToBottom = () => {
    };
    const messageElements = messages.map((message) => {
        const subtext = message.messageStatus && (
            <span className="status">
                {message.messageStatus}
            </span>
        );
        return (
            <div
                key={message.id}
                className="message"
            >
                <MessageBubble {...message} />
                {subtext}
            </div>
        );
    });
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