import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import MessageBubble from './MessageBubble';

import './MessageContainer.css';

const MessageContainer = ({
    messages,
}) => {
    const endRef = useRef(null);
    useEffect(() => {
        if (messages.length > 0) {
            endRef.current.scrollIntoView({ behavior: "smooth" });
        }
    }, [messages]);

    const messageElements = messages.map((message, index) => {
        const subtext = message.messageStatus && message.direction === 'outbound' && (
            <span className="status">
                {message.messageStatus}
            </span>
        );
        return (
            <div
                key={message.id}
                className={`message message-${index}`}
                ref={index >= messages.length - 1 ? endRef : null}
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