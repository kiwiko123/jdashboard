import React from 'react';
import PropTypes from 'prop-types';
import messagePropTypeShape from './propTypes/messagePropTypeShape';

const ChatroomMessageFeed = ({
  messages,
}) => {
  return (
    <div className="ChatroomMessageFeed">
        Messages
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