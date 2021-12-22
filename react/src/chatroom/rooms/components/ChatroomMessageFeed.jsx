import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import messagePropTypeShape from './propTypes/messagePropTypeShape';
import ChatroomMessage from './ChatroomMessage';

import './ChatroomMessageFeed.css';

function createMessage(message, ref) {
    const className = classnames({
        inbound: message.direction === 'inbound',
        outbound: message.direction === 'outbound',
    });
    return (
        <ChatroomMessage
            key={message.chatroomMessage.id}
            className={className}
            ref={ref}
            message={message.chatroomMessage.message}
            caption={message.caption}
        />
    );
}

const ChatroomMessageFeed = ({
    messages,
}) => {
    const endRef = useRef(null);
    useEffect(() => {
        if (endRef.current && messages.length > 0) {
            endRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    }, [messages]);

    const messageElements = messages.map((message, index) => {
        const ref = (index >= messages.length - 1) ? endRef : null;
        return createMessage(message, ref);
    });
    return (
        <div className="ChatroomMessageFeed">
            {messageElements}
        </div>
    );
};

ChatroomMessageFeed.propTypes = {
    messages: PropTypes.arrayOf(PropTypes.shape(messagePropTypeShape)),
};

ChatroomMessageFeed.defaultProps = {
    messages: [],
};

export default ChatroomMessageFeed;