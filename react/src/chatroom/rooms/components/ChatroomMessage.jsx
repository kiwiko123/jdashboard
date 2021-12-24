import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import messagePropTypeShape from './propTypes/messagePropTypeShape';

import './ChatroomMessage.css';

const ChatroomMessage = ({
  message, caption, className, ref,
}) => {
    const divClassName = classnames('ChatroomMessage', className);
    const captionArea = caption && (
        <div className="caption">
            {caption}
        </div>
    );

    return (
        <div
            className={divClassName}
            ref={ref}
        >
            <div className="message">
                {message}
            </div>
            {captionArea}
        </div>
    );
};

ChatroomMessage.propTypes = {
    message: PropTypes.string.isRequired,
    caption: PropTypes.node,
    className: PropTypes.string,
};

ChatroomMessage.defaultProps = {
    caption: null,
    className: null,
};

export default ChatroomMessage;