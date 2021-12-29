import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import messagePropTypeShape from './propTypes/messagePropTypeShape';
import ChatroomMessage from './ChatroomMessage';

import './ChatroomMessageFeed.css';

function createStatusIcon({ direction, messageStatus }) {
    if (direction !== 'outbound') {
        return null;
    }

    let iconClassName;
    switch (messageStatus) {
        case 'sent':
            iconClassName = 'far fa-paper-plane';
            break;
        case 'delivered':
            iconClassName = 'fas fa-check';
            break;
        case 'read':
            iconClassName = 'fas fa-user-check';
            break;
        default:
            return null;
    }

    return (
        <i className={iconClassName} />
    );

}

function createMessage(message, ref) {
    const className = classnames({
        inbound: message.direction === 'inbound',
        outbound: message.direction === 'outbound',
    });
    const caption = createStatusIcon({
        direction: message.direction,
        messageStatus: message.chatroomMessage.messageStatus,
    });

    return (
        <ChatroomMessage
            key={message.chatroomMessage.id}
            className={className}
            ref={ref}
            message={message.chatroomMessage.message}
            caption={caption}
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