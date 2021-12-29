import React from 'react';
import PropTypes from 'prop-types';
import ChatroomInboxItem from './ChatroomInboxItem';
import inboxItemPropTypeShape from './propTypes/inboxItemPropTypeShape';

import './ChatroomInbox.css';

const ChatroomInbox = ({
    inboxItems,
}) => {
    const itemElements = inboxItems.map((item) => (
        <ChatroomInboxItem
            {...item}
            key={item.room.id}
        />
    ));
    return (
        <div className="ChatroomInbox">
            {itemElements}
        </div>
    );
};

ChatroomInbox.propTypes = {
    inboxItems: PropTypes.arrayOf(PropTypes.shape(inboxItemPropTypeShape)),
};

ChatroomInbox.defaultProps = {
    inboxItems: [],
};

export default ChatroomInbox;