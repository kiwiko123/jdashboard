import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import messagePropType from './propTypes/messagePropType';

import './MessageBubble.css';

const MessageBubble = ({
    id, message, messageStatus, direction, senderName, className,
}) => {
    const divClassName = classnames('MessageBubble', className, {
        inbound: direction === 'inbound',
        outbound: direction === 'outbound',
    });
    return (
        <div className={divClassName}>
            {message}
        </div>
    );
}

MessageBubble.propTypes = {
    id: PropTypes.number.isRequired,
    message: PropTypes.string.isRequired,
    messageStatus: PropTypes.string.isRequired,
    direction: PropTypes.oneOf(['inbound', 'outbound']).isRequired,
    senderName: PropTypes.string,
    className: PropTypes.string,
};

MessageBubble.defaultProps = {
    senderName: null,
    className: null,
};

export default MessageBubble;