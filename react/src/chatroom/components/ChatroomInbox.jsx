import React from 'react';
import PropTypes from 'prop-types';

const ChatroomInbox = ({
    inboxItems,
}) => {
    const itemElements = inboxItems.map((item) => (
        <div key={item.room.id}>
            {item.room.id}
        </div>
    ));
    return (
        <div className="ChatroomInbox">
            {itemElements}
        </div>
    );
};

ChatroomInbox.propTypes = {
    inboxItems: PropTypes.array,
};

ChatroomInbox.defaultProps = {
    inboxItems: [],
};

export default ChatroomInbox;